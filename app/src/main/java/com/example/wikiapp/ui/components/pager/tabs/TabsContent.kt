package com.example.wikiapp.ui.components.pager.tabs

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.wikiapp.ui.components.pager.item.TabItem
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState

@ExperimentalPagerApi
@Composable
fun TabsContent(navController: NavController, tabs: List<TabItem>, pagerState: PagerState) {
    HorizontalPager(state = pagerState) { page ->
        tabs[page].screen(navController)
    }
}