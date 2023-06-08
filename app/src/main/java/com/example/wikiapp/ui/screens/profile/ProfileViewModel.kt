package com.example.wikiapp.ui.screens.profile

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apollographql.apollo3.api.ApolloResponse
import com.example.wikiapp.*
import com.example.wikiapp.dagger.AppComponent
import com.example.wikiapp.domain.IWikiRepository
import com.example.wikiapp.state.Session
import com.example.wikiapp.utils.MutableResultFlow
import com.example.wikiapp.utils.loadOrError
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProfileViewModel(appComponent: AppComponent = WikiApp.appComponent) : ViewModel() {

    @Inject
    lateinit var session: Session

    @Inject
    lateinit var repository: IWikiRepository

    val location by lazy { session.location }
    val getPageListByCreatorResult =
        MutableResultFlow<ApolloResponse<PagesListByCreatorQuery.Data>>()
    val getUsersProfileResult = MutableResultFlow<ApolloResponse<UsersProfileQuery.Data>>()
    val getCommentsListResult = MutableResultFlow<ApolloResponse<CommentsListQuery.Data>>()
    private val nameUpdateResult = MutableResultFlow<ApolloResponse<UpdateNameMutation.Data>>()
    private val locationUpdateResult = MutableResultFlow<ApolloResponse<UpdateLocationMutation.Data>>()
    private val jobUpdateResult = MutableResultFlow<ApolloResponse<UpdateJobMutation.Data>>()
    private val timezoneUpdateResult = MutableResultFlow<ApolloResponse<UpdateTimezoneMutation.Data>>()
    private val dateFormatUpdateResult = MutableResultFlow<ApolloResponse<UpdateDateFormatMutation.Data>>()
    private val appearanceUpdateResult = MutableResultFlow<ApolloResponse<UpdateAppearanceMutation.Data>>()

    var dName by mutableStateOf(getUsersProfileResult.value.data?.data?.users?.profile?.name)
    var dLocation by mutableStateOf(getUsersProfileResult.value.data?.data?.users?.profile?.location)
    var dJobTitle by mutableStateOf(getUsersProfileResult.value.data?.data?.users?.profile?.jobTitle)
    var dTimezone by mutableStateOf(getUsersProfileResult.value.data?.data?.users?.profile?.timezone)

    var dDateFormat by mutableStateOf(getUsersProfileResult.value.data?.data?.users?.profile?.dateFormat)
    var dAppearance by mutableStateOf(getUsersProfileResult.value.data?.data?.users?.profile?.appearance)

    init {
        appComponent.inject(this)
    }

    fun onOpen() {
        getUsersProfile()
    }

    fun changeToken(newToken: String) = session.changeToken(newToken)

    fun changeLocation(newLocation: String) = session.changeLocation(newLocation)

    fun getPageListByCreator(id: Int) = viewModelScope.launch {
        getPageListByCreatorResult.loadOrError {
            repository.getPageListByCreator(id)
        }
    }

    private fun getUsersProfile() = viewModelScope.launch {
        getUsersProfileResult.loadOrError {
            repository.getUsersProfile()
        }
    }

    fun updateName(
        name: String
    ) = viewModelScope.launch {
        nameUpdateResult.loadOrError {
            repository.updateName(name = name)
        }
    }

    fun updateLocation(
        location: String
    ) = viewModelScope.launch {
        locationUpdateResult.loadOrError {
            repository.updateLocation(location = location)
        }
    }

    fun updateJob(
        job: String?
    ) = viewModelScope.launch {
        jobUpdateResult.loadOrError {
            repository.updateJob(job = job)
        }
    }

    fun updateTimezone(
        timezone: String?
    ) = viewModelScope.launch {
        timezoneUpdateResult.loadOrError {
            repository.updateTimezone(timezone = timezone)
        }
    }

    fun updateDateFormat(
        dateFormat: String?
    ) = viewModelScope.launch {
        dateFormatUpdateResult.loadOrError {
            repository.updateDateFormat(dateFormat = dateFormat)
        }
    }

    fun updateAppearance(
        appearance: String?
    ) = viewModelScope.launch {
        appearanceUpdateResult.loadOrError {
            repository.updateAppearance(appearance = appearance)
        }
    }
}