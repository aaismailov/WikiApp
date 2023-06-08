package com.example.wikiapp.ui.screens.newpage

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apollographql.apollo3.api.ApolloResponse
import com.example.wikiapp.CreateNewPageMutation
import com.example.wikiapp.UsersProfileQuery
import com.example.wikiapp.WikiApp
import com.example.wikiapp.dagger.AppComponent
import com.example.wikiapp.domain.IWikiRepository
import com.example.wikiapp.state.Session
import com.example.wikiapp.utils.MutableResultFlow
import com.example.wikiapp.utils.loadOrError
import kotlinx.coroutines.launch
import javax.inject.Inject

class NewPageViewModel(appComponent: AppComponent = WikiApp.appComponent) : ViewModel() {

    @Inject
    lateinit var session: Session

    @Inject
    lateinit var repository: IWikiRepository

    val location by lazy { session.location }
    val getUsersProfileResult = MutableResultFlow<ApolloResponse<UsersProfileQuery.Data>>()
    val createNewPageResult = MutableResultFlow<ApolloResponse<CreateNewPageMutation.Data>>()

    var dTitle by mutableStateOf("")
    var dDescription by mutableStateOf("")
    var dLanguage by mutableStateOf("ru")
    var dPath by mutableStateOf("test/test/useless")
    var dTags by mutableStateOf("")
    var dEditor by mutableStateOf("markdown")
    var dIsPublished by mutableStateOf(false)
    var dIsPrivate by mutableStateOf(false)

    init {
        appComponent.inject(this)
    }

    fun onOpen() {
        getUsersProfile()
    }

    fun changeToken(newToken: String) = session.changeToken(newToken)

    fun changeLocation(newLocation: String) = session.changeLocation(newLocation)

    private fun getUsersProfile() = viewModelScope.launch {
        getUsersProfileResult.loadOrError {
            repository.getUsersProfile()
        }
    }

    fun createNewPage(
        content: String,
        description: String,
        editor: String,
        isPublished: Boolean,
        isPrivate: Boolean,
        locale: String,
        path: String,
        tags: List<String>,
        title: String
    ) = viewModelScope.launch {
        createNewPageResult.loadOrError {
            repository.createNewPage(content, description, editor, isPublished,
                isPrivate, locale, path, tags, title)
        }
    }
}