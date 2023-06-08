package com.example.wikiapp.ui.components.pager.tabscreens.pages

import android.os.Build
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.wikiapp.PagesListByCreatorQuery
import com.example.wikiapp.R
import com.example.wikiapp.ui.components.pager.item.MainTitleWithButton
import com.example.wikiapp.ui.components.pager.tabscreens.pages.expandablelist.ExpandableCard
import com.example.wikiapp.ui.screens.main.Routes
import com.example.wikiapp.ui.theme.lightGray
import com.example.wikiapp.ui.theme.middleGray

@ExperimentalMaterialApi
@Composable
fun PagesTab(
    navController: NavController,
    pagesList: List<PagesListByCreatorQuery.List>
) {
    val scrollState = rememberScrollState()

    val listOfPagesIsEmpty = "Список пуст"
    val listOfCreatedPages = "Список страниц, которые я создал(-а) или изменял(-а)"

    Column(
        modifier = Modifier
            .background(lightGray)
    ) {

        MainTitleWithButton(
            idImg = R.drawable.update,
            title = listOfCreatedPages,
            isButtonSave = false,
        )

        Row(
            modifier = Modifier
                .background(Color.Green)
        ) {
            Column(
                modifier = Modifier
                    .background(lightGray)
                    .fillMaxWidth()
                    .verticalScroll(scrollState)
            ) {

                if (pagesList.isEmpty()) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = listOfPagesIsEmpty,
                            fontSize = 10.sp,
                            color = middleGray
                        )
                    }
                } else {
                    pagesList.forEach { item ->
                        item.title?.let {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                ExpandableCard(
                                    openLink = { navController.navigate("${Routes.home}/${item.id}") },
                                    title = it,
                                    path = item.path,
                                    createdAt = item.createdAt.toString(),
                                    updatedAt = item.updatedAt.toString()
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}