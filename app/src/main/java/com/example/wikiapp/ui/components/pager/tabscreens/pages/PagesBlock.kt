package com.example.wikiapp.ui.components.pager.tabscreens.pages

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.wikiapp.PagesListByCreatorQuery
import com.example.wikiapp.ui.screens.profile.ProfileViewModel
import com.example.wikiapp.utils.SuccessResult

@ExperimentalMaterialApi
@Composable
fun PageBlock(navController: NavController) {
    val viewModel: ProfileViewModel = viewModel()

    val pagesList = remember { mutableStateOf(emptyList<PagesListByCreatorQuery.List>()) }

    val getUsersProfileResult by viewModel.getUsersProfileResult.collectAsState()

    val getPagesListByCreatorResult by viewModel.getPageListByCreatorResult.collectAsState()

    if (getPagesListByCreatorResult is SuccessResult) {
        pagesList.value = getPagesListByCreatorResult.data?.data?.pages!!.list
    }

    LaunchedEffect(getUsersProfileResult.data?.data?.users?.profile) {
        getUsersProfileResult.data?.data?.users?.profile?.let { viewModel.getPageListByCreator(it.id) }
    }

    PagesTab(navController = navController, pagesList = pagesList.value)
}