package com.example.wikiapp.ui.screens.search

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apollographql.apollo3.api.ApolloResponse
import com.example.wikiapp.SearchPageQuery
import com.example.wikiapp.WikiApp
import com.example.wikiapp.dagger.AppComponent
import com.example.wikiapp.domain.IWikiRepository
import com.example.wikiapp.utils.MutableResultFlow
import com.example.wikiapp.utils.loadOrError
import kotlinx.coroutines.launch
import javax.inject.Inject

class SearchViewModel(appComponent: AppComponent = WikiApp.appComponent) : ViewModel() {

    @Inject
    lateinit var repository: IWikiRepository

    val searchPageResult = MutableResultFlow<ApolloResponse<SearchPageQuery.Data>>()
    private val _searchTextState = mutableStateOf(value = "")
    val searchTextState = _searchTextState

    init {
        appComponent.inject(this)
    }

    fun searchPage(query: String) = viewModelScope.launch {
        searchPageResult.loadOrError {
            repository.searchPage(query)
        }
    }

    fun updateSearchTextState(newValue: String) {
        _searchTextState.value = newValue
    }
}