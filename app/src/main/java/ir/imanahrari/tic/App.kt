package ir.imanahrari.tic

import android.app.Application
import io.github.inflationx.calligraphy3.CalligraphyConfig
import io.github.inflationx.calligraphy3.CalligraphyInterceptor
import io.github.inflationx.viewpump.ViewPump
import ir.imanahrari.tic.service.utilities.openDatabase

class App: Application(){
    override fun onCreate() {
        super.onCreate()
        ViewPump.init(
            ViewPump.builder()
                .addInterceptor(
                    CalligraphyInterceptor(
                        CalligraphyConfig.Builder()
                            .setDefaultFontPath("fonts/byecan.ttf")
                            .build()
                    )
                )
                .build()
        )

        openDatabase()
    }
}