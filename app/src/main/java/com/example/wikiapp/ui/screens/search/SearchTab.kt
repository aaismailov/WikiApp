package com.example.wikiapp.ui.screens.search

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.wikiapp.R
import com.example.wikiapp.SearchPageQuery
import com.example.wikiapp.data.models.PageShortInfo
import com.example.wikiapp.ui.components.divider.DividerProfile
import com.example.wikiapp.ui.components.pager.item.ContentRowWithoutImage
import com.example.wikiapp.ui.components.pager.item.MainTitleWithButton
import com.example.wikiapp.ui.screens.main.Routes
import com.example.wikiapp.ui.theme.Shapes
import com.example.wikiapp.ui.theme.lightGray
import com.example.wikiapp.ui.theme.middleGray

@ExperimentalMaterialApi
@Composable
fun SearchTab(
    pagesList: List<SearchPageQuery.Result?>,
    navController: NavController,
    titleFontWeight: FontWeight = FontWeight.Bold
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .background(lightGray)
    ) {

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
                            text = "Нет страниц, соответствующих вашему запросу.",
                            fontSize = 10.sp,
                            color = middleGray
                        )
                    }
                } else {
                    pagesList.forEach { item ->
                        if (item != null) {
                            Card(
                                modifier = Modifier
                                    .padding(vertical = 5.dp, horizontal = 10.dp)
                                    .animateContentSize(
                                        animationSpec = tween(
                                            durationMillis = 300,
                                            easing = LinearOutSlowInEasing
                                        )
                                    ),
                                shape = Shapes.small,
                                onClick = {
                                    navController.navigate("${Routes.home}/${item.id}")
                                }
                            ) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(12.dp)
                                ) {
                                    Text(
                                        text = item.title,
                                        fontSize = 14.sp,
                                        fontWeight = titleFontWeight,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                    ContentRowWithoutImage(
                                        title = "Описание",
                                        subtitle = item.description
                                    )
                                    DividerProfile()
                                    ContentRowWithoutImage(
                                        title = "Путь",
                                        subtitle = item.path
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}