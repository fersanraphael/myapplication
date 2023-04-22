package com.example.myapplication

import android.app.Application
import com.example.myapplication.framework.di.myApplicationModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

/**
 * @author Raphael Fersan
 */
internal class MyApplicationApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(applicationContext)
            androidLogger(Level.DEBUG)

            modules(
                listOf(myApplicationModule)
            )
        }
    }
}
