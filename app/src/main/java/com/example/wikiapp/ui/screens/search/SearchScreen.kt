package com.example.wikiapp.ui.screens.search

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.wikiapp.ui.components.loader.CircularLoader
import com.example.wikiapp.ui.theme.ApolloSampleTheme
import com.example.wikiapp.ui.theme.middleGray
import com.example.wikiapp.utils.LoadingResult
import com.example.wikiapp.utils.SuccessResult
import com.example.wikiapp.utils.subscribeOnError

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SearchScreen(
    navController: NavController,
    showMessage: (message: Int) -> Unit = {}
) {
    val scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Closed))

    val viewModel: SearchViewModel = viewModel()
    val searchTextState by viewModel.searchTextState

    val searchPageResult by viewModel.searchPageResult.collectAsState()
    searchPageResult.subscribeOnError(showMessage)

    if (searchPageResult is LoadingResult) CircularLoader()

    Scaffold(
        topBar = {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                elevation = AppBarDefaults.TopAppBarElevation,
                color = Color.Black,
                contentColor = Color.White
            ) {
                TextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = searchTextState,
                    onValueChange = {
                        viewModel.updateSearchTextState(it)
                        if (searchTextState.length >= 2) {
                            viewModel.searchPage(searchTextState)
                        }
                    },
                    placeholder = {
                        Text(
                            modifier = Modifier
                                .alpha(ContentAlpha.medium),
                            text = "Search here...",
                            color = Color.White
                        )
                    },
                    textStyle = TextStyle(
                        fontSize = MaterialTheme.typography.subtitle1.fontSize
                    ),
                    singleLine = true,
                    leadingIcon = {
                        IconButton(
                            modifier = Modifier
                                .alpha(ContentAlpha.medium),
                            onClick = {}
                        ) {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = "Search Icon",
                                tint = Color.White
                            )
                        }
                    },
                    trailingIcon = {
                        IconButton(
                            onClick = {
                                if (searchTextState.isNotEmpty()) {
                                    viewModel.updateSearchTextState("")
                                } else {
                                    navController.popBackStack()
                                }
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Close Icon",
                                tint = Color.White
                            )
                        }
                    },
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Search
                    ),
                    keyboardActions = KeyboardActions(
                        onSearch = {
                            if (searchTextState.length >= 2) {
                                viewModel.searchPage(searchTextState)
                            }
                        }
                    ),
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color.Transparent,
                        cursorColor = Color.White.copy(alpha = ContentAlpha.medium)
                    )
                )
            }
        },
        scaffoldState = scaffoldState
    ) {
        if (searchTextState.length < 2) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(30.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Введите 2 или более символа, чтобы начать поиск...",
                    fontSize = 20.sp,
                    color = middleGray,
                    textAlign = TextAlign.Center
                )
            }
        } else if (searchPageResult is SuccessResult) {
            val results = searchPageResult.data?.data?.pages?.search?.results ?: listOf()
            SearchTab(pagesList = results, navController = navController)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ApolloSampleTheme {
        val navController = rememberNavController()
        SearchScreen(navController = navController)
    }
}