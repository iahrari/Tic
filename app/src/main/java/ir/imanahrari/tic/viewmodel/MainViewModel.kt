package ir.imanahrari.tic.viewmodel

import android.content.Context
import android.content.DialogInterface
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ir.imanahrari.tic.service.database.ADatabase
import ir.imanahrari.tic.service.model.LessonModel
import ir.imanahrari.tic.service.utilities.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class MainViewModel: ViewModel(), WebViewUtility.OnHtmlDataProcessed{

    val dataLive: MutableLiveData<List<LessonModel>> = MutableLiveData()
    private val needDialog: MutableLiveData<Boolean> = MutableLiveData()
    val isHtmlProcessed: MutableLiveData<Boolean> = MutableLiveData()

    private lateinit var webView: WebViewUtility

    val weekLive: MutableLiveData<String> = MutableLiveData()
    private var c: Context? = null


    override fun onHtmlDataProcessed(data: MutableList<LessonModel>, week: String) = runBlocking{
        isHtmlProcessed.value = true
        dataLive.value = data
        weekLive.value = week

       saveToDB(data)
        deleteExtraRowsFromDB(data)
        c!!.saveWeek(week)
    }

    fun setContext(context: Context) = runBlocking{
        needDialog.observeForever {if(it) setDialog() }
        c = context
        isHtmlProcessed.value = false
        if(context.isInternetConnected())
            setWebView()

        else{
            if(context.isUserLogin())
                withContext(Dispatchers.Default){
                    weekLive.postValue(context.getWeek())
                    val data = ADatabase.INSTANCE!!.getDAO().getAll()
                    if(data.isEmpty())
                        needDialog.postValue(true)
                    else {
                        dataLive.postValue(data)
                        isHtmlProcessed.postValue(true)
                    }
                }
            else
                needDialog.value = true
        }
    }

    private fun setWebView(){
        webView = WebViewUtility(c!!, this)
    }

    private fun setDialog(){
        AlertDialog.Builder(c!!)
            .setTitle("اینترنت موجود نیست!")
            .setMessage("لطفا برای دریافت اطلاعات به اینترنت متصل شوید.")
            .setPositiveButton("متصل شد") { _: DialogInterface, _: Int ->
                setWebView()
            }.show()
    }
}