package uz.unidev.dictionary.app

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidFileProperties
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber
import uz.unidev.dictionary.BuildConfig
import uz.unidev.dictionary.di.adapterModule
import uz.unidev.dictionary.di.repositoryModule
import uz.unidev.dictionary.di.roomModule
import uz.unidev.dictionary.di.viewModelModule

/**
 *  Created by Nurlibay Koshkinbaev on 31/07/2022 01:01
 */

class App : Application() {

    companion object {
        lateinit var instance: App
    }

    override fun onCreate() {
        super.onCreate()
        instance = this

        val modules = listOf(viewModelModule, roomModule, repositoryModule, adapterModule)

        startKoin {
            androidLogger()
            androidContext(this@App)
            androidFileProperties()
            koin.loadModules(modules)
        }

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}