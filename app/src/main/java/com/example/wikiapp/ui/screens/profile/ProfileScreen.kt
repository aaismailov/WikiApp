package com.example.wikiapp.ui.screens.profile

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.wikiapp.ui.components.appbars.WikiAppBar
import com.example.wikiapp.ui.components.pager.PagerTabs
import com.example.wikiapp.ui.components.pager.item.TabItem
import com.example.wikiapp.ui.theme.*
import com.example.wikiapp.utils.subscribeOnError
import com.google.accompanist.pager.*
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun ProfileScreen(
    navController: NavController,
    showMessage: (message: Int) -> Unit = {}
) {
    val scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Closed))
    val viewModel: ProfileViewModel = viewModel()
    val screenScope = LocalLifecycleOwner.current
    var showWebView by remember { mutableStateOf(false) }
    val getUsersProfileResult by viewModel.getUsersProfileResult.collectAsState()
    getUsersProfileResult.subscribeOnError(showMessage)

    LaunchedEffect(Unit) {
        viewModel.onOpen()
    }

    val tabs = listOf(
        TabItem.Profile,
        TabItem.Pages
    )

    val pagerState = rememberPagerState(pageCount = tabs.size)
    Scaffold(
        topBar = {
            WikiAppBar(
                userName = getUsersProfileResult.data?.data?.users?.profile?.name ?: "",
                userEmail = getUsersProfileResult.data?.data?.users?.profile?.email ?: "",
                logoutOnClick = {
                    screenScope.lifecycleScope.launch {
                        viewModel.changeLocation("https://wiki.miem.hse.ru/logout")
                        viewModel.changeToken("")
                        showWebView = true
                    }
                },
                navController = navController,
            )
        },
        scaffoldState = scaffoldState,
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            PagerTabs(navController = navController, tabs = tabs, pagerState = pagerState)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ApolloSampleTheme {
        val navController = rememberNavController()
        ProfileScreen(navController = navController)
    }
}