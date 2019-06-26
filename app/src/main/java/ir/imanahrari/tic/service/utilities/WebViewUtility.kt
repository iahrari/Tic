package ir.imanahrari.tic.service.utilities

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.util.Log
import android.view.View
import android.webkit.JavascriptInterface
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import ir.imanahrari.tic.service.model.ClassModel
import ir.imanahrari.tic.service.model.LessonModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

@SuppressLint("SetJavaScriptEnabled", "AddJavascriptInterface")
class WebViewUtility(val context: Context, private var listener: OnHtmlDataProcessed) {
    private var webView: WebView = WebView(context)
    private var timesToTry = 0

    private val javascriptGetHtml = "(function() { return ('<html>'+document.getElementsByTagName('html')[0].innerHTML+'</html>'); })();"
    private val baseUrl = "http://sturc.birjand.ac.ir/"

    fun destroyWebView(){
        webView.destroy()
    }

    private fun setWebViewSettings(){
        webView.settings.javaScriptEnabled = true
        if (Build.VERSION.SDK_INT < 19)
            webView.addJavascriptInterface(this@WebViewUtility, "HTMLOUT")
    }

    private fun setWebViewClient(){
        webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
                    view!!.evaluateJavascript(javascriptGetHtml) { logHtml(it) }

                if (url == baseUrl) {
                    login()

                } else if (url!!.contains("${baseUrl}panel/index.php")) {
                    getData()
                }
            }
        }
    }

    init {
        setWebViewSettings()
        setWebViewClient()
        webView.loadUrl(baseUrl)
    }

    @JavascriptInterface
    fun htmlProcess(html: String) = runBlocking{
        val list: MutableList<LessonModel> = ArrayList()
        val classList: MutableList<ClassModel> = ArrayList()
        var week = ""
        var isAbsent = true
        withContext(Dispatchers.Default) {
            for ((index, row) in prepareDocument(html).select("tr").withIndex()) {
                val columns = row.select("td")
                if (index > 2) {
                    when {
                        columns[0].text() == "رديف" -> isAbsent = false
                        isAbsent -> list.add(prepareLessonModel(columns))
                        else -> classList.add(prepareClassMode(columns))
                    }
                } else if(index == 1){
                    week = columns[2].text()
                }
            }
        }
        listener.onHtmlDataProcessed(list, classList, week)
    }

    fun logHtml(html: String){
        Log.i("html", html)
        if(html.contains("your access is denied for 24 hr"))
            Toast.makeText(context, "در ارتباط شما با سامانه تیک مشکلی به وجود آمده است لطفا در 24 ساعت آینده برای ورود تلاشش کنید.", Toast.LENGTH_LONG).show()
        else if(html.contains("با عرض پوزش به دلیل همگام سازی با گلستان تا ساعاتی دیگر امکان دسترسی به پنل وجود ندارد")) {
            Toast.makeText(
                context,
                "با عرض پوزش به دلیل همگام سازی با گلستان تا ساعاتی دیگر امکان دسترسی به پنل وجود ندارد",
                Toast.LENGTH_LONG
            ).show()
            listener.onProblemsHappen()
        }
    }

    fun login(){
        webView.apply {
            timesToTry++
            if (timesToTry > 1)
                return listener.onLogInFailed()

            val data =
                "javascript:document.getElementById('user').value = '" +
                        context.getUser() +
                "';document.getElementById('pass').value = '" +
                        context.getPass() +
                        "';document.getElementById('btnlogin').click();"

            if (Build.VERSION.SDK_INT >= 19)
                evaluateJavascript(data){}
            else
                loadUrl(data)
        }
    }

    fun getData(){
        webView.apply {
            if (Build.VERSION.SDK_INT >= 19)
                evaluateJavascript(javascriptGetHtml) { htmlProcess(it) }
            else
                loadUrl("javascript:window.HTMLOUT.processHTML('<html>'+document.getElementsByTagName('html')[0].innerHTML+'</html>');")
        }
    }

    interface OnHtmlDataProcessed{
        fun onHtmlDataProcessed(data: MutableList<LessonModel>, classData: MutableList<ClassModel>, week: String)
        fun onProblemsHappen()
        fun onLogInFailed()
    }
}