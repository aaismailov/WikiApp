package com.example.wikiapp.ui.components.pager

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.wikiapp.ui.components.pager.item.TabItem
import com.example.wikiapp.ui.components.pager.tabs.Tabs
import com.example.wikiapp.ui.components.pager.tabs.TabsContent
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState

@ExperimentalPagerApi
@Composable
fun PagerTabs(navController: NavController, tabs: List<TabItem>, pagerState: PagerState) {
    Column {
        Tabs(tabs = tabs, pagerState = pagerState)
        TabsContent(navController = navController, tabs = tabs, pagerState = pagerState)
    }

}