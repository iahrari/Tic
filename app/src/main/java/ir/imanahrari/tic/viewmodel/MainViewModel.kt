package ir.imanahrari.tic.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ir.imanahrari.tic.service.database.ADatabase
import ir.imanahrari.tic.service.model.ClassModel
import ir.imanahrari.tic.service.model.LessonModel
import ir.imanahrari.tic.service.utilities.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class MainViewModel: ViewModel(), WebViewUtility.OnHtmlDataProcessed{
    override fun onLogInFailed() {
        isLogInFailed.postValue(true)
    }

    override fun onProblemsHappen() {
        offlineMode()
        webView.destroyWebView()
    }

    val dataLive: MutableLiveData<List<LessonModel>> = MutableLiveData()
    val needDialog: MutableLiveData<Boolean> = MutableLiveData()
    val isHtmlProcessed: MutableLiveData<Boolean> = MutableLiveData()
    val classDataLive: MutableLiveData<List<ClassModel>> = MutableLiveData()
    val isOnline: MutableLiveData<Boolean> = MutableLiveData()
    val isLogInFailed: MutableLiveData<Boolean> = MutableLiveData()
    private lateinit var webView: WebViewUtility

    val weekLive: MutableLiveData<String> = MutableLiveData()
    private var c: Context? = null


    override fun onHtmlDataProcessed(data: MutableList<LessonModel>, classData: MutableList<ClassModel>, week: String) = runBlocking{
        isHtmlProcessed.value = true
        dataLive.value = data
        weekLive.value = week
        classDataLive.value = classData
        saveToDB(data)
        saveClassesToDB(classData)
        deleteExtraRowsFromDB(data)
        deleteExtraClassRowsFromDB(classData)
        c!!.saveWeek(week)
        webView.destroyWebView()
    }

    fun setContext(context: Context) = runBlocking{
        c = context
        isHtmlProcessed.value = false
        if(context.isInternetConnected()) {
            setWebView()
            isOnline.postValue(true)
        } else{
            if(context.isUserLogin())
                offlineMode()

            else
                needDialog.value = true
        }
    }

    private fun offlineMode() = runBlocking{
        withContext(Dispatchers.Default) {
            weekLive.postValue(c!!.getWeek())
            val data = ADatabase.INSTANCE!!.getDAO().getAll()
            val classData = ADatabase.INSTANCE!!.getClassDAO().getAll()
            if (data.isEmpty())
                needDialog.postValue(false)
            else {
                dataLive.postValue(data)
                isHtmlProcessed.postValue(true)
                classDataLive.postValue(classData)
                isOnline.postValue(false)
            }
        }
    }

    private fun setWebView(){
        webView = WebViewUtility(c!!, this)
    }


}