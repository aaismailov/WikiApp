package com.example.wikiapp.ui.components.drawer

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.wikiapp.PagesListQuery
import com.example.wikiapp.PagesTreeQuery
import com.example.wikiapp.R
import com.example.wikiapp.data.models.NavItem
import com.example.wikiapp.data.models.getIcon
import com.example.wikiapp.ui.components.drawer.item.DrawerItem
import com.example.wikiapp.ui.screens.home.HomeViewModel
import com.example.wikiapp.ui.theme.backgroundBlue
import com.example.wikiapp.ui.theme.brightBlue
import com.example.wikiapp.ui.theme.darkBlue
import com.example.wikiapp.ui.theme.textGray
import com.example.wikiapp.utils.SuccessResult
import com.example.wikiapp.utils.getIdFromUrl
import com.example.wikiapp.utils.subscribeOnError
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

@Composable
fun Drawer(
    scope: CoroutineScope,
    scaffoldState: ScaffoldState,
    pageLists: List<PagesListQuery.List>,
    pageTree: List<PagesTreeQuery.Tree>,
    navItems: () -> List<NavItem>,
    currentPageId: () -> Int,
    getSinglePage: (id: Int) -> Unit
) {
    var changeMenu by remember { mutableStateOf(false) }
    val viewModel2: HomeViewModel = viewModel()

    val pageTree2 = remember { mutableStateOf(emptyList<PagesTreeQuery.Tree>()) }

    val getPageTreeResult by viewModel2.getPageTreeResult.collectAsState()

//    if (getPageTreeResult is SuccessResult) {
//        pageTree2.value = getPageTreeResult.data?.data?.pages?.tree?.filterNotNull()!!
//    }

    Column(
        modifier = Modifier
            .background(color = darkBlue)
            .fillMaxHeight()
            .verticalScroll(rememberScrollState())
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .background(brightBlue),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Button(
                onClick = {
                    getSinglePage(0)
                },
                colors = ButtonDefaults.buttonColors(backgroundColor = backgroundBlue)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_home),
                    contentDescription = ""
                )
            }

            Button(
                onClick = {
                    changeMenu = !changeMenu
                },
                colors = ButtonDefaults.buttonColors(backgroundColor = backgroundBlue)
            ) {
                Row(
                    modifier = Modifier
                        .background(backgroundBlue),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Image(
                        painter = if (changeMenu) painterResource(id = R.drawable.navigation)
                        else painterResource(id = R.drawable.review_tree),
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(Color.White),
                        modifier = Modifier
                            .padding(end = 10.dp)
                    )
                    Text(
                        text = if (changeMenu) "Главное меню" else "Обзор",
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }

        if (changeMenu) {
            pageTree.forEach { item ->
                val itemPageId = getIdFromUrl(item.path, pageLists)

                DrawerItem(
                    item = item,
                    iconId = R.drawable.folder,
                    text = item.title,
                    selected = currentPageId() == itemPageId,
                    onItemClick = {
                        if (item.isFolder == false) {
                            getSinglePage(itemPageId)
                        } else {
                            if (item.path == "groups") {

                                viewModel2.getPageTree(1)
                                if (getPageTreeResult is SuccessResult) {
                                    pageTree2.value = getPageTreeResult.data?.data?.pages?.tree?.filterNotNull()!!
                                  //  pageTree2.value += getPageTreeResult.data?.data?.pages?.tree?.filterNotNull()!!
                                }
                              //  Log.d("Draw", viewModel.getPageTreeResult.value.toString())
                            Log.d("Draw", pageTree2.value.toString())
                                pageTree2.value.forEach{
                                        item ->
                                    Log.d("Draw", item.title)

                                }
                            }

                        }

                        Log.d("Drawer", itemPageId.toString())
                        scope.launch {
                            scaffoldState.drawerState.close()
                        }
                    }
                ) // TODO Если folder, то открыть папку (с иерархией и оформлением), если page, то открыть страницу (иконки соотв.)
            }
        } else {
            navItems().forEach { item ->
                when (item.k) {
                    "header" -> {
                        Text(
                            text = item.l,
                            color = textGray,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(start = 10.dp, top = 15.dp, bottom = 5.dp)
                        )
                    }
                    "link" -> {
                        val itemPageId = getIdFromUrl(item.t, pageLists)
                        DrawerItem(
                            item = item,
                            iconId = item.getIcon(),
                            text = item.l,
                            selected = currentPageId() == itemPageId,
                            onItemClick = {
                                getSinglePage(itemPageId)
                                scope.launch {
                                    scaffoldState.drawerState.close()
                                }
                            }
                        )
                    }
                    "divider" -> {
                        Divider(
                            color = textGray,
                            thickness = (0.5).dp,
                            modifier = Modifier.padding(top = 10.dp)
                        )
                    }
                }
            }
        }
    }
}