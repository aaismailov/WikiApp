package com.example.wikiapp.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apollographql.apollo3.api.ApolloResponse
import com.example.wikiapp.*
import com.example.wikiapp.dagger.AppComponent
import com.example.wikiapp.data.models.PageShortInfo
import com.example.wikiapp.domain.IWikiRepository
import com.example.wikiapp.state.Session
import com.example.wikiapp.utils.MutableResultFlow
import com.example.wikiapp.utils.loadOrError
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeViewModel(appComponent: AppComponent = WikiApp.appComponent) : ViewModel() {

    @Inject
    lateinit var session: Session

    @Inject
    lateinit var repository: IWikiRepository

    val location by lazy { session.location }
    val getPageListResult = MutableResultFlow<ApolloResponse<PagesListQuery.Data>>()
    val getPageTreeResult = MutableResultFlow<ApolloResponse<PagesTreeQuery.Data>>()
    val getUsersProfileResult = MutableResultFlow<ApolloResponse<UsersProfileQuery.Data>>()
    val getCommentsListResult = MutableResultFlow<ApolloResponse<CommentsListQuery.Data>>()
    val getSinglePageResult = MutableResultFlow<ApolloResponse<SinglePageQuery.Data>>()
    val commentCreateResult = MutableResultFlow<ApolloResponse<CommentCreateMutation.Data>>()

    init {
        appComponent.inject(this)
    }

    fun onOpen() {
        getPageList()
        getPageTree(0)
        getUsersProfile()
    }

    fun isPagesStackEmpty() = session.pagesStack.value.isEmpty()

    fun changeToken(newToken: String) = session.changeToken(newToken)

    fun changeLocation(newLocation: String) = session.changeLocation(newLocation)

    private fun getPageList() = viewModelScope.launch {
        getPageListResult.loadOrError {
            repository.getPageList()
        }
    }

     fun getPageTree(id: Int) = viewModelScope.launch {
        getPageTreeResult.loadOrError {
            repository.getPageTree(id)
        }
    }

    private fun getUsersProfile() = viewModelScope.launch {
        getUsersProfileResult.loadOrError {
            repository.getUsersProfile()
        }
    }

    fun getCommentsList(path: String) = viewModelScope.launch {
        getCommentsListResult.loadOrError {
            repository.getCommentsList(path)
        }
    }

    fun commentCreate(
        content: String,
        name: String?,
        email: String?
    ) = viewModelScope.launch {
        commentCreateResult.loadOrError {
            repository.commentCreate(getCurrentPageId(), content, name, email)
        }
    }

    fun getSinglePage(id: Int = ID, isBack: Boolean = false) = viewModelScope.launch {
        getSinglePageResult.loadOrError {
            val calledId = if (id == 0) ID else id
            if (!isBack) {
                session.addPagesStack(calledId)
            }
            repository.getSinglePage(calledId)
        }
    }

    fun goBack() {
        session.dropLastPagesStack()
        if (session.pagesStack.value.isEmpty()) {
            getSinglePage(isBack = true)
        } else {
            getSinglePage(getCurrentPageId(), true)
        }
    }

    fun likePage(page: PageShortInfo) = session.likePage(page)

    fun unlikePage(id: Int) = session.unlikePage(id)

    fun isPageLiked(id: Int) = session.isPageLiked(id)

    fun getCurrentPageId() = session.getCurrentPageId()

    companion object {
        // Для тестов id:
        // html:     7827 edu/tracks/networking/students19 Поток 19х специализации Сети
        // markdown: 25   docs/miem-digital                Сервисы МИЭМ
        const val ID = 7827
    }
}