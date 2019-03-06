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
import ir.imanahrari.tic.service.model.LessonModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

@SuppressLint("SetJavaScriptEnabled", "AddJavascriptInterface")
class WebViewUtility(val context: Context, private var listener: OnHtmlDataProcessed) {
    private var webView: WebView = WebView(context)
    private var timesToTry = 0

    init {
        webView.apply {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1)
                layoutDirection = View.LAYOUT_DIRECTION_RTL

            settings.javaScriptEnabled = true

            if (Build.VERSION.SDK_INT < 19)
                addJavascriptInterface(this@WebViewUtility, "HTMLOUT")

            webViewClient = object : WebViewClient() {
                override fun onPageFinished(view: WebView?, url: String?) {
                    super.onPageFinished(view, url)
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
                        view!!.evaluateJavascript("(function() { return ('<html>'+document.getElementsByTagName('html')[0].innerHTML+'</html>'); })();") { logHtml(it) }

                    if (url == "http://sturc.birjand.ac.ir/") {
                        login()

                    } else if (url!!.contains("sturc.birjand.ac.ir/panel/index.php")) {
                        getData()
                    }
                }
            }
            loadUrl("http://sturc.birjand.ac.ir/")
        }
    }

    @JavascriptInterface
    fun htmlProcess(html: String) = runBlocking{
        val list: MutableList<LessonModel> = ArrayList()
        var week = ""

        withContext(Dispatchers.Default) {
            for ((index, row) in prepareDocument(html).select("tr").withIndex()) {
                val columns = row.select("td")
                if (index > 2) {
                    list.add(prepareLessonModel(columns))
                } else if(index == 1){
                    week = columns[2].text()
                }
            }
        }
        listener.onHtmlDataProcessed(list, week)
    }

    fun logHtml(html: String){
        Log.i("html", html)
        if(html.contains("your access is denied for 24 hr"))
            Toast.makeText(context, "در ارتباط شما با سامانه تیک مشکلی به وجود آمده است لطفا در 24 ساعت آینده برای ورود تلاشش کنید.", Toast.LENGTH_LONG).show()
    }

    fun login(){
        webView.apply {
            timesToTry++
            if (timesToTry > 1){
                Toast.makeText(context!!, "اطلاعات وارد شده درست نیست.", Toast.LENGTH_LONG).show()
                return
            }
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
                evaluateJavascript(
                    "(function() { return ('<html>'+document.getElementsByTagName('html')[0].innerHTML+'</html>'); })();"
                ) { htmlProcess(it) }
            else
                loadUrl("javascript:window.HTMLOUT.processHTML('<html>'+document.getElementsByTagName('html')[0].innerHTML+'</html>');")
        }
    }

    interface OnHtmlDataProcessed{
        fun onHtmlDataProcessed(data: MutableList<LessonModel>, week: String)
    }
}