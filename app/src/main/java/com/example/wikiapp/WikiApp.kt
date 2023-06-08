package com.example.wikiapp

import android.app.Application
import com.example.wikiapp.dagger.AppComponent
import com.example.wikiapp.dagger.DaggerAppComponent

class WikiApp : Application() {

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent
            .builder()
            .context(this)
            .build()
    }

    companion object {
        lateinit var appComponent: AppComponent
    }
}