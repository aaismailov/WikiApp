package com.example.wikiapp.ui.screens.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apollographql.apollo3.api.ApolloResponse
import com.example.wikiapp.AuthLoginMutation
import com.example.wikiapp.WikiApp
import com.example.wikiapp.dagger.AppComponent
import com.example.wikiapp.domain.IWikiRepository
import com.example.wikiapp.state.Session
import com.example.wikiapp.utils.MutableResultFlow
import com.example.wikiapp.utils.loadOrError
import kotlinx.coroutines.launch
import javax.inject.Inject

class AuthViewModel(appComponent: AppComponent = WikiApp.appComponent) : ViewModel() {

    @Inject
    lateinit var session: Session

    @Inject
    lateinit var repository: IWikiRepository

    val location by lazy { session.location }
    val authLoginResult = MutableResultFlow<ApolloResponse<AuthLoginMutation.Data>>()

    init {
        appComponent.inject(this)
    }

    fun changeToken(newToken: String) = session.changeToken(newToken)

    fun changeLocation(newLocation: String) = session.changeLocation(newLocation)

    fun authLogin() = viewModelScope.launch {
        authLoginResult.loadOrError {
            changeLocation("")
            repository.authLogin()
        }
    }
}