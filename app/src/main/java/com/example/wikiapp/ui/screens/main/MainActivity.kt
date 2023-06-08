package com.example.wikiapp.ui.screens.main

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.wikiapp.observeconnectivity.ConnectionState
import com.example.wikiapp.observeconnectivity.connectivityState
import com.example.wikiapp.ui.components.loader.CircularLoader
import com.example.wikiapp.ui.screens.auth.AuthorizationScreen
import com.example.wikiapp.ui.screens.favourite.FavouriteScreen
import com.example.wikiapp.ui.screens.home.HomeScreen
import com.example.wikiapp.ui.screens.newpage.NewPage
import com.example.wikiapp.ui.screens.profile.ProfileScreen
import com.example.wikiapp.ui.screens.search.SearchScreen
import com.example.wikiapp.ui.screens.wifi.WifiConnection
import com.example.wikiapp.ui.theme.ApolloSampleTheme
import kotlinx.coroutines.launch


class MainActivity : ComponentActivity() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val scaffoldState = rememberScaffoldState()
            val navController = rememberNavController()
            val viewModel: MainViewModel = viewModel()
            val connection by connectivityState()
            var isInternet by remember { mutableStateOf(false) }

            ApolloSampleTheme {
                Scaffold(
                    scaffoldState = scaffoldState,
                    snackbarHost = {
                        SnackbarHost(
                            hostState = it,
                            modifier = Modifier.navigationBarsPadding()
                        ) { snackBarData ->
                            Snackbar(
                                snackbarData = snackBarData,
                                backgroundColor = MaterialTheme.colors.surface,
                                contentColor = contentColorFor(MaterialTheme.colors.surface),
                                shape = MaterialTheme.shapes.small
                            )
                        }
                    },
                    content = {
                        Surface(
                            modifier = Modifier.fillMaxSize(),
                            color = MaterialTheme.colors.background
                        ) {
                            if (connection == ConnectionState.Available) {
                                MainScreen(
                                    viewModel = viewModel,
                                    scaffoldState = scaffoldState,
                                    navController = navController,
                                    isInternet = isInternet
                                )
                            } else {
                                isInternet = true
                                CircularLoader()
                                WifiConnection()
                            }
                        }
                    }
                )

            }

        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainScreen(
    viewModel: MainViewModel,
    scaffoldState: ScaffoldState,
    navController: NavHostController,
    isInternet: Boolean
) {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val showMessage: (Int) -> Unit = { message ->
        val strMessage = context.getString(message)
        scope.launch {
            scaffoldState.snackbarHostState.showSnackbar(strMessage)
        }
    }

    Surface(
        color = MaterialTheme.colors.background,
        modifier = Modifier
            .fillMaxSize()
    ) {
        NavHost(
            navController = navController,
            startDestination = if (viewModel.token.collectAsState().value.isNotEmpty()) "${Routes.home}/{id}" else Routes.authorization
        ) {
            composable(Routes.authorization) {
                AuthorizationScreen(
                    showMessage = showMessage
                )
            }
            composable("${Routes.home}/{id}",
                arguments = listOf(navArgument("id") { type = NavType.IntType })
            ) { backStackEntry ->
                Log.d("page", backStackEntry.arguments?.getInt("id").toString())
                HomeScreen(
                    navController = navController,
                    showMessage = showMessage,
                    isInternet = isInternet,
                    currentPageId = backStackEntry.arguments?.getInt("id") ?: 0
                )
            }
            composable(Routes.profile) {
                ProfileScreen(
                    navController = navController,
                    showMessage = showMessage
                )
            }
            composable(Routes.newPage) {
                NewPage(
                    navController = navController,
                    showMessage = showMessage
                )
            }
            composable(Routes.favourite) {
                FavouriteScreen(
                    navController = navController
                )
            }
            composable(Routes.search) {
                SearchScreen(
                    navController = navController,
                    showMessage = showMessage
                )
            }
        }
    }
}

object Routes {
    const val authorization = "authorization"
    const val home = "home"
    const val profile = "profile"
    const val newPage = "newPage"
    const val favourite = "favourite"
    const val search = "search"
}