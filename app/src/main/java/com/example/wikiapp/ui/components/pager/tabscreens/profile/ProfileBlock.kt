package com.example.wikiapp.ui.components.pager.tabscreens.profile

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.wikiapp.ui.screens.profile.ProfileViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ProfileBlock() {
    val viewModel: ProfileViewModel = viewModel()

    val getUsersProfileResult by viewModel.getUsersProfileResult.collectAsState()

    val getCommentsListResult by viewModel.getCommentsListResult.collectAsState()

    val dProfile = getUsersProfileResult.data?.data?.users?.profile

    ProfileTab(
        userName = viewModel.dName ?: dProfile?.name ?: "",
        providerName = dProfile?.providerName ?: "",
        location = viewModel.dLocation ?: dProfile?.location ?: "",
        jobTitle = viewModel.dJobTitle ?: dProfile?.jobTitle ?: "",
        timezone = viewModel.dTimezone ?: dProfile?.timezone ?: "",
        dateFormat = viewModel.dDateFormat ?: dProfile?.dateFormat ?: "",
        appearance = viewModel.dAppearance ?: dProfile?.appearance ?: "",
        createdAt = (dProfile?.createdAt ?: "").toString(),
        updatedAt = (dProfile?.updatedAt ?: "").toString(),
        lastLoginAt = (dProfile?.lastLoginAt ?: "").toString(),
        groups = dProfile?.groups,
        pagesTotal = (dProfile?.pagesTotal ?: "").toString(),
        commentsTotal = (getCommentsListResult.data?.data?.comments?.list?.size ?: "0").toString()
    )
}