package com.example.wikiapp.ui.components.pager.item

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.wikiapp.R
import com.example.wikiapp.ui.components.pager.tabscreens.pages.PageBlock
import com.example.wikiapp.ui.components.pager.tabscreens.profile.ProfileBlock


typealias ComposableFun = @Composable (NavController) -> Unit
sealed class TabItem(var icon: Int, var title: String, var screen: ComposableFun) {

    object Profile : TabItem(R.drawable.ic_person, "Профиль", { ProfileBlock() });
    @OptIn(ExperimentalMaterialApi::class)
    object Pages : TabItem(R.drawable.ic_library_books, "Страницы", { PageBlock(it) })
}