package com.example.wikiapp.ui.components.pager.tabscreens.pages.expandablelist

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.wikiapp.R
import com.example.wikiapp.ui.components.divider.DividerProfile
import com.example.wikiapp.ui.components.pager.item.ContentRowWithoutImage
import com.example.wikiapp.ui.theme.Shapes
import  com.example.wikiapp.ui.components.func.FormattedDateTime
import com.example.wikiapp.ui.screens.main.Routes
import com.example.wikiapp.ui.screens.newpage.NewPageViewModel
import com.example.wikiapp.ui.screens.profile.ProfileViewModel

@RequiresApi(Build.VERSION_CODES.O)
@ExperimentalMaterialApi
@Composable
fun ExpandableCard(
    openLink: () -> Unit,
    title: String,
    path: String,
    createdAt: String,
    updatedAt: String,
    titleFontWeight: FontWeight = FontWeight.Bold
) {
    val viewModel: ProfileViewModel = viewModel()
    val getUsersProfileResult by viewModel.getUsersProfileResult.collectAsState()
    val curFormat = getUsersProfileResult.data?.data?.users?.profile?.dateFormat.toString()
    var expandedState by remember { mutableStateOf(false) }
    val rotationState by animateFloatAsState(
        targetValue = if (expandedState) 180f else 0f
    )

    val fdt = FormattedDateTime()
    val titlePath = "Путь"
    val titleCreated = "Создан"
    val defaultDateFormat = "DD.MM.YYYY"
    val titleLastUpdate = "Последнее обновление"

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
            expandedState = !expandedState
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    modifier = Modifier.weight(6f),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = title,
                        fontSize = 14.sp,
                        fontWeight = titleFontWeight,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.width(3.dp))
                    IconButton(
                        onClick = {
                            openLink()
                        }
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.external_link),
                            contentDescription = null,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.weight(3f))
                IconButton(
                    modifier = Modifier
                        .alpha(ContentAlpha.medium)
                        .weight(1f)
                        .rotate(rotationState),
                    onClick = {
                        expandedState = !expandedState
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = "DropDownArrow"
                    )
                }
            }
            if (expandedState) {
                ContentRowWithoutImage(
                    title = titlePath,
                    subtitle = path
                )
                DividerProfile()
                ContentRowWithoutImage(
                    title = titleCreated,
                    subtitle = if (createdAt.isNotEmpty()) {
                        if (viewModel.dDateFormat.isNullOrEmpty())
                            fdt.formattedDate(
                                createdAt,
                                curFormat.ifEmpty { defaultDateFormat }, true
                            ) else
                            fdt.formattedDate(
                                createdAt,
                                viewModel.dDateFormat.toString(), true
                            )
                    } else ""
                )
                DividerProfile()
                ContentRowWithoutImage(
                    title = titleLastUpdate,
                    subtitle = if (updatedAt.isNotEmpty()) {
                        if (viewModel.dDateFormat.isNullOrEmpty())
                            fdt.formattedDate(
                                updatedAt,
                                curFormat.ifEmpty { defaultDateFormat }, true
                            ) else
                            fdt.formattedDate(
                                updatedAt,
                                viewModel.dDateFormat.toString(), true
                            )
                    } else ""
                )
            }
        }
    }
}