package ir.imanahrari.tic.service.utilities

import android.content.Context
import android.content.DialogInterface
import android.net.ConnectivityManager
import android.os.Build
import androidx.appcompat.app.AlertDialog
import androidx.core.content.edit
import ir.imanahrari.tic.R
import ir.imanahrari.tic.service.database.ADatabase
import ir.imanahrari.tic.ui.view.LoginDialog
import ir.imanahrari.tic.ui.view.MainActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.util.*

fun Context.isUserLogin(): Boolean{
    return getSharedPreferences("User", Context.MODE_PRIVATE)
        .getString("user", "") != ""
}

fun Context.setUser(user: String, pass: String){
    getSharedPreferences("User", Context.MODE_PRIVATE).edit {
        putString("user", user)
        putString("pass", pass)
    }
}

fun Context.getUser(): String{
    return getSharedPreferences("User", Context.MODE_PRIVATE)
        .getString("user", "")!!
}

fun Context.getPass(): String{
    return getSharedPreferences("User", Context.MODE_PRIVATE)
        .getString("pass", "")!!
}

fun MainActivity.deleteUser() = runBlocking{
    withContext(Dispatchers.Default){
        ADatabase.INSTANCE!!.apply {
            getDAO().deleteAll()
            getClassDAO().deleteAll()
        }
    }
    setUser("", "")
    signUpUser()
    true
}

@Suppress("DEPRECATION")
fun Context.setRtl(){
    val conf = resources.configuration
    val locale = Locale("fa")

    Locale.setDefault(locale)

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
        conf.setLocale(locale)
    else
        conf.locale = locale

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1)
        createConfigurationContext(conf)
    else
        resources.updateConfiguration(conf, resources.displayMetrics)
}

fun Context.openSimpleDialog(
    title: Int,
    body: Int,
    positiveText: Int,
    positiveAction: DialogInterface.OnClickListener)
        : Boolean{
    return AlertDialog.Builder(this)
        .setTitle(title)
        .setMessage(body)
        .setPositiveButton(positiveText, positiveAction)
        .show()
        .isShowing
}

fun Context.isInternetConnected(): Boolean{
    val activeNetwork = (getSystemService (Context.CONNECTIVITY_SERVICE) as (ConnectivityManager)).activeNetworkInfo
    return activeNetwork != null && activeNetwork.isConnected
}

fun Context.saveWeek(week: String){
    getSharedPreferences("week", Context.MODE_PRIVATE)
        .edit()
        .putString("week", week)
        .apply()
}

fun Context.getWeek(): String{
    return getSharedPreferences("week", Context.MODE_PRIVATE)
        .getString("week", "")!!
}

fun MainActivity.startLoginIn(){
    if(!isUserLogin())
        signUpUser()
    else
        viewModel.setContext(this)
}

fun Context.openDatabase(){
    ADatabase.getInstance(this)
}

fun MainActivity.signUpUser(){
    LoginDialog(this).show(supportFragmentManager, "login")
}