package com.example.wikiapp.ui.screens.favourite

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.wikiapp.ui.theme.*
import com.google.accompanist.pager.*

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun FavouriteScreen(navController: NavController) {
    val scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Closed))
    val viewModel: FavouriteViewModel = viewModel()

    val likeStack by viewModel.likeStack.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            Icons.Filled.ArrowBack,
                            contentDescription = "Назад",
                            tint = Color.White
                        )
                    }
                    Text(text = "ИЗБРАННОЕ")
                },
                backgroundColor = Color.Black,
                contentColor = Color.White,
                elevation = 12.dp
            )
        },
        scaffoldState = scaffoldState
    ) {
        FavPageTab(pagesList = likeStack, navController = navController)
    }

}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ApolloSampleTheme {
        val navController = rememberNavController()
        FavouriteScreen(navController = navController)
    }
}