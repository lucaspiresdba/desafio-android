package com.picpay.desafio.android.app

import android.app.Application
import br.com.lucaspires.data.di.dataModule
import br.com.lucaspires.domain.di.domainModule
import com.facebook.stetho.Stetho
import com.picpay.desafio.android.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class PicPayApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Stetho.initializeWithDefaults(this)
        startKoin {
            androidContext(this@PicPayApplication)
            modules(listOf(appModule, domainModule, dataModule))
        }
    }
}