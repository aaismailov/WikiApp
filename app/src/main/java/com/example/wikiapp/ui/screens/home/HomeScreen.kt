package com.example.wikiapp.ui.screens.home

import android.util.Base64
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.wikiapp.CommentsListQuery
import com.example.wikiapp.PagesListQuery
import com.example.wikiapp.PagesTreeQuery
import com.example.wikiapp.R
import com.example.wikiapp.data.models.NavItem
import com.example.wikiapp.data.models.PageShortInfo
import com.example.wikiapp.ui.components.MDDocument
import com.example.wikiapp.ui.components.appbars.WikiAppBar
import com.example.wikiapp.ui.components.breadcrumb.BreadCrumb
import com.example.wikiapp.ui.components.comment.CommentBlock
import com.example.wikiapp.ui.components.drawer.Drawer
import com.example.wikiapp.ui.components.fabs.BottomFabs
import com.example.wikiapp.ui.components.loader.CircularLoader
import com.example.wikiapp.ui.components.texts.ScreenDescription
import com.example.wikiapp.ui.components.texts.ScreenTitle
import com.example.wikiapp.ui.components.webview.CustomWebView
import com.example.wikiapp.ui.components.webview.HtmlContentView
import com.example.wikiapp.ui.theme.ApolloSampleTheme
import com.example.wikiapp.utils.LoadingResult
import com.example.wikiapp.utils.SuccessResult
import com.example.wikiapp.utils.subscribeOnError
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.commonmark.node.Document
import org.commonmark.parser.Parser
import org.jsoup.Jsoup

@Composable
fun HomeScreen(
    navController: NavHostController,
    showMessage: (message: Int) -> Unit = {},
    isInternet: Boolean,
    currentPageId: Int
) {
    val scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Closed))
    val scope = rememberCoroutineScope()
    val scrollState = remember { ScrollState(0) }
    val viewModel: HomeViewModel = viewModel()

    val location by viewModel.location.collectAsState()

    val getSinglePageResult by viewModel.getSinglePageResult.collectAsState()
    getSinglePageResult.subscribeOnError(showMessage)

    val getPageListResult by viewModel.getPageListResult.collectAsState()
    getPageListResult.subscribeOnError(showMessage)

    val getPageTreeResult by viewModel.getPageTreeResult.collectAsState()
    getPageTreeResult.subscribeOnError(showMessage)

    val getUsersProfileResult by viewModel.getUsersProfileResult.collectAsState()
    getUsersProfileResult.subscribeOnError(showMessage)

    val getCommentsListResult by viewModel.getCommentsListResult.collectAsState()
    getCommentsListResult.subscribeOnError(showMessage)

    val pageLists = remember { mutableStateOf(emptyList<PagesListQuery.List>()) }
    val pageTree = remember { mutableStateOf(emptyList<PagesTreeQuery.Tree>()) }
    val commentsList = remember { mutableStateOf(emptyList<CommentsListQuery.List>()) }
    var showWebView by remember { mutableStateOf(false) }
    var jsonNav by remember { mutableStateOf(emptyList<NavItem>()) }

    val isLoading = getSinglePageResult is LoadingResult || getPageListResult is LoadingResult ||
            getPageTreeResult is LoadingResult || getUsersProfileResult is LoadingResult ||
            getCommentsListResult is LoadingResult

    val screenScope = LocalLifecycleOwner.current

    LaunchedEffect(Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            val doc = Jsoup.connect("https://wiki.miem.hse.ru").get()
            val root = doc.getElementById("root")
            val pageElement = root.getElementsByTag("page")[0]
            val byteArrayNav = Base64.decode(pageElement.attr("sidebar"), Base64.DEFAULT)
            val strNav = String(byteArrayNav, Charsets.UTF_8)
            jsonNav = Gson().fromJson(strNav, object : TypeToken<List<NavItem>>() {}.type)
        }
        viewModel.onOpen()
        if (!isInternet || (viewModel.isPagesStackEmpty())
        ) {
            viewModel.getSinglePage(currentPageId)
        }
    }

    if (getPageListResult is SuccessResult) {
        pageLists.value = getPageListResult.data?.data?.pages?.list!!
    }

    if (getPageTreeResult is SuccessResult) {
        pageTree.value = getPageTreeResult.data?.data?.pages?.tree?.filterNotNull()!!
    }

    if (getCommentsListResult is SuccessResult) {
        commentsList.value = getCommentsListResult.data?.data?.comments?.list?.filterNotNull()!!
    }

    if (isLoading) CircularLoader()

    getSinglePageResult.also { result ->
        if (result is SuccessResult) {
            val singlePageData = getSinglePageResult.data?.data?.pages?.single
            val errSinglePage = getSinglePageResult.data?.errors?.get(0)?.message
            var content = singlePageData?.content ?: errSinglePage.toString()
            val pagePath = singlePageData?.path?.split("/")
            var likeState by remember { mutableStateOf(viewModel.isPageLiked(singlePageData?.id ?: 0)) }

            LaunchedEffect(singlePageData?.path) {
                if (singlePageData?.path != null) {
                    viewModel.getCommentsList(singlePageData.path)
                }
            }

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
                        navController = navController
                    )
                },
                scaffoldState = scaffoldState,
                floatingActionButton = {
                    BottomFabs(
                        scope = scope,
                        scaffoldState = scaffoldState,
                        scrollState = scrollState
                    )
                },
                drawerContent = {
                    Drawer(
                        scope = scope,
                        scaffoldState = scaffoldState,
                        pageLists = pageLists.value,
                        pageTree = pageTree.value,
                        navItems = { jsonNav },
                        currentPageId = viewModel::getCurrentPageId,
                        getSinglePage = viewModel::getSinglePage
                    )
                }
            ) {
                Box(modifier = Modifier.fillMaxSize()) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(scrollState)
                    ) {
                        // Breadcrumb navigation
                        BreadCrumb(
                            pagePath = pagePath,
                            pageLists = pageLists.value,
                            getSinglePage = viewModel::getSinglePage
                        )

                        // Like, title with description
                        Row(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            IconButton(
                                onClick = {
                                    if (likeState) {
                                        if (singlePageData?.id != null) {
                                            viewModel.unlikePage(singlePageData.id)
                                        }
                                    } else {
                                        viewModel.likePage(
                                            PageShortInfo(
                                                singlePageData?.id ?: 0,
                                                singlePageData?.path ?: "",
                                                singlePageData?.title ?: "",
                                                singlePageData?.description ?: "",
                                            )
                                        )
                                    }
                                    likeState = !likeState
                                },
                                modifier = Modifier.align(Alignment.CenterVertically)
                            ) {
                                Icon(
                                    painter = painterResource(R.drawable.star),
                                    contentDescription = null,
                                    modifier = Modifier.size(20.dp),
                                    tint = if (likeState) Color.Blue else Color.Gray
                                )
                            }
                            Column {
                                ScreenTitle(
                                    text = singlePageData?.title ?: "Заголовок"
                                )
                                ScreenDescription(
                                    text = singlePageData?.description ?: "Описание"
                                )
                            }
                        }

                        // Content
                        if (singlePageData?.contentType == "markdown") {
                            val parser = Parser.builder().build()
                            val root = parser.parse(content) as Document
                            Box(modifier = Modifier.padding(start = 16.dp, end = 16.dp)) {
                                Column {
                                    MDDocument(navController, root)
                                }
                            }
                        } else {
                            var showHtmlContentView by remember { mutableStateOf(true) }
                            content =
                                content.replace("href=\"/", "href=\"https://wiki.miem.hse.ru/")
                            if (showHtmlContentView) {
                                HtmlContentView(content) {
                                    showHtmlContentView = false
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(20.dp))

                        CommentBlock(
                            userName = getUsersProfileResult.data?.data?.users?.profile?.name ?: "",
                            userEmail = getUsersProfileResult.data?.data?.users?.profile?.email
                                ?: "",
                            commentsList = commentsList.value,
                            commentCreate = viewModel::commentCreate
                        )

                        Spacer(modifier = Modifier.height(20.dp))
                    }
                }
            }
        }
    }

    if (showWebView) {
        CustomWebView(
            location = location
        ) {
            showWebView = false
        }
    }

    BackHandler(
        enabled = true,
        onBack = viewModel::goBack
    )
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ApolloSampleTheme {
        val navController = rememberNavController()
        HomeScreen(navController = navController, isInternet = false, currentPageId = 0)
    }
}