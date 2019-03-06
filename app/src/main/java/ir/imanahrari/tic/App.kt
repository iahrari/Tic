package ir.imanahrari.tic

import android.app.Application
import android.content.res.Configuration
import android.os.Build
import ir.imanahrari.tic.service.database.ADatabase
import java.util.*

@Suppress("DEPRECATION", "unused")
class App: Application() {
//    private lateinit var conf : Configuration
//
//    override fun onCreate() {
//        super.onCreate()
//        ADatabase.getInstance(this)
//        conf = baseContext.resources.configuration
//        val locale = Locale("fa")
//
//        Locale.setDefault(locale)
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
//            conf.setLocale(locale)
//        else
//            conf.locale = locale
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1)
//            baseContext.createConfigurationContext(conf)
//        else
//            baseContext.resources.updateConfiguration(conf, baseContext.resources.displayMetrics)
//    }
//
//    override fun onConfigurationChanged(newConfig: Configuration?) {
//        super.onConfigurationChanged(conf)
//
//        val locale = Locale("fa")
//
//        Locale.setDefault(locale)
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
//            conf.setLocale(locale)
//        else
//            conf.locale = locale
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1)
//            baseContext.createConfigurationContext(conf)
//        else
//            baseContext.resources.updateConfiguration(conf, baseContext.resources.displayMetrics)
//    }
}