package com.example.wikiapp.ui.screens.favourite

import androidx.lifecycle.ViewModel
import com.example.wikiapp.WikiApp
import com.example.wikiapp.dagger.AppComponent
import com.example.wikiapp.state.Session
import javax.inject.Inject

class FavouriteViewModel(appComponent: AppComponent = WikiApp.appComponent) : ViewModel() {

    @Inject
    lateinit var session: Session

    val likeStack by lazy { session.likeStack }

    init {
        appComponent.inject(this)
    }
}