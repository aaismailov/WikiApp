package com.example.wikiapp.ui.screens.main

import androidx.lifecycle.ViewModel
import com.example.wikiapp.WikiApp
import com.example.wikiapp.dagger.AppComponent
import com.example.wikiapp.state.Session
import javax.inject.Inject

class MainViewModel(appComponent: AppComponent = WikiApp.appComponent) : ViewModel() {

    @Inject
    lateinit var session: Session

    val token by lazy { session.token }

    init {
        appComponent.inject(this)
    }

    fun clearPagesStack() = session.clearPagesStack()
}