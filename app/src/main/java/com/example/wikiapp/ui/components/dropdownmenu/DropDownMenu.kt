package com.example.wikiapp.ui.components.dropdownmenu

import android.os.Build
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.wikiapp.ui.screens.profile.ProfileViewModel
import java.time.LocalTime
import java.time.OffsetTime
import java.time.ZoneOffset
import  com.example.wikiapp.ui.components.func.FormattedDateTime
import com.example.wikiapp.ui.screens.newpage.NewPageViewModel
import com.example.wikiapp.ui.theme.lightBlue

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DropDownMenu(
    nameOfScreen: String,
    nameOfMenu: String,
    title: String
) {
    val fdt = FormattedDateTime()
    val zoneNamesMap = fdt.getZoneNameMap()
    val viewModel: ProfileViewModel = viewModel()
    val getUsersProfileResult by viewModel.getUsersProfileResult.collectAsState()
    var isExpanded by remember { mutableStateOf(false) }

    val listOfTimezones = mutableListOf<String>()
    val listOfFormatDate = listOf(
        "Формат по умолчанию", "DD/MM/YYYY", "DD.MM.YYYY", "MM/DD/YYYY",
        "YYYY-MM-DD", "YYYY/MM/DD"
    )
    val listOfAppearance = listOf("Сайт по умолчанию", "Светлый режим", "Темный режим")
    var curList = listOf<String>()

    var selectedTimezone by remember { mutableStateOf(getUsersProfileResult.data?.data?.users?.profile?.timezone) }
    var selectedDateFormat by remember { mutableStateOf(getUsersProfileResult.data?.data?.users?.profile?.dateFormat) }
    var selectedAppearance by remember { mutableStateOf(getUsersProfileResult.data?.data?.users?.profile?.appearance) }

    var textFieldSize by remember { mutableStateOf(Size.Zero) }

    val icon = if (isExpanded)
        Icons.Filled.KeyboardArrowUp
    else
        Icons.Filled.KeyboardArrowDown

    for ((key, value) in zoneNamesMap) {
        listOfTimezones.add(value)
    }

    val titleTimezone = "Часовой пояс"
    val titleDateFormat = "Формат даты"
    val titleAppearance = "Внешний вид"
    val titleDateFormatDefault = "Формат по умолчанию"
    val titleAppearanceLightEng = "light"
    val titleAppearanceDarkEng = "dark"
    val titleAppearanceLightRus = "Светлый режим"
    val titleAppearanceDarkRus = "Темный режим"
    val titleAppearanceDefault = "Сайт по умолчанию"

    var editor by remember { mutableStateOf("markdown") }
    val viewModelNewPage: NewPageViewModel = viewModel()

    Column(Modifier.padding(20.dp)) {

        OutlinedTextField(
            readOnly = true,
            value =
            when (nameOfScreen) {
                "profile" -> {
                    when (title) {
                        titleTimezone -> {
                            if (viewModel.dTimezone.isNullOrEmpty())
                                zoneNamesMap.filterKeys { it == selectedTimezone.toString() }.values.elementAt(
                                    0
                                ) else
                                zoneNamesMap.filterKeys { it == viewModel.dTimezone.toString() }.values.elementAt(
                                    0
                                )

                        }
                        titleDateFormat -> {
                            if (viewModel.dDateFormat.isNullOrEmpty()) {
                                when (selectedDateFormat) {
                                    "" -> titleDateFormatDefault
                                    else -> {
                                        selectedDateFormat
                                    }
                                }
                            } else {
                                when (viewModel.dDateFormat) {
                                    "" -> titleDateFormatDefault
                                    else -> {
                                        viewModel.dDateFormat.toString()
                                    }
                                }
                            }
                        }

                        else -> {
                            if (viewModel.dAppearance.isNullOrEmpty()) {
                                when (selectedAppearance) {
                                    titleAppearanceLightEng -> titleAppearanceLightRus
                                    titleAppearanceDarkEng -> titleAppearanceDarkRus
                                    else -> {
                                        titleAppearanceDefault
                                    }
                                }
                            } else {
                                when (viewModel.dAppearance) {
                                    titleAppearanceLightEng -> titleAppearanceLightRus
                                    titleAppearanceDarkEng -> titleAppearanceDarkRus
                                    else -> {
                                        titleAppearanceDefault
                                    }
                                }
                            }
                        }
                    }.toString()
                }
                "newPage" -> {
                    when (nameOfMenu) {
                        "editor" -> {
                            viewModelNewPage.dEditor
                        }
                        "language" -> {
                            viewModelNewPage.dLanguage
                        } else -> ""
                    }
                } else -> ""
            },
            onValueChange = {
                when (nameOfScreen) {
                    "profile" -> {
                        when (title) {
                            titleTimezone -> selectedTimezone = it
                            titleDateFormat -> selectedDateFormat = if (it == "")
                                titleDateFormatDefault else it

                            else -> selectedAppearance =
                                when (it) {
                                    titleAppearanceLightEng -> titleAppearanceLightRus
                                    titleAppearanceDarkEng -> titleAppearanceDarkRus
                                    else -> {
                                        titleAppearanceDefault
                                    }
                                }.toString()
                        }
                    }
                    "newPage" -> {
                        when (nameOfMenu) {
                            "editor" -> {
                                viewModelNewPage.dEditor = it
                            }
                            "language" -> {
                                viewModelNewPage.dLanguage = it
                            } else -> {}
                        }
                    } else -> {}
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                //.height(25.dp)
                .onGloballyPositioned { coordinates ->
                    textFieldSize = coordinates.size.toSize()
                }
            ,
            label = { Text(text = title) },
            trailingIcon = {
                Icon(icon, "contentDescription",
                    Modifier.clickable { isExpanded = !isExpanded }
                      //  .width(25.dp).height(25.dp)
                )
            }
        )

        DropdownMenu(
            expanded = isExpanded,
            onDismissRequest = { isExpanded = false },
            modifier =
            Modifier
                .width(with(LocalDensity.current) { textFieldSize.width.toDp() }
                )
        ) {

            when (nameOfScreen) {
                "profile" -> {
                    when (title) {
                        titleTimezone -> curList = listOfTimezones
                        titleDateFormat -> curList = listOfFormatDate
                        titleAppearance -> curList = listOfAppearance
                    }
                    curList.forEach { label ->
                        DropdownMenuItem(
                            modifier =
                            if (
                                viewModel.dDateFormat.isNullOrEmpty() && label == selectedDateFormat
                                || viewModel.dAppearance.isNullOrEmpty() && label == selectedAppearance
                            ) {
                                Modifier.background(lightBlue)
                            } else if (
                                label == viewModel.dDateFormat
                                || label == viewModel.dAppearance
                            )
                                Modifier.background(lightBlue) else
                                Modifier.background(Color.White),

                            onClick = {
                                when (title) {
                                    titleTimezone -> {
                                        selectedTimezone =
                                            zoneNamesMap.filterValues { it == label }.keys.elementAt(0)
                                        viewModel.dTimezone = selectedTimezone
                                    }
                                    titleDateFormat -> {
                                        selectedDateFormat = if (label == titleDateFormatDefault) ""
                                        else label
                                        viewModel.dDateFormat = selectedDateFormat
                                    }
                                    titleAppearance -> {
                                        selectedAppearance =
                                            when (label) {
                                                titleAppearanceLightRus -> titleAppearanceLightEng
                                                titleAppearanceDarkRus -> titleAppearanceDarkEng
                                                else -> {
                                                    ""
                                                }
                                            }
                                        viewModel.dAppearance = selectedAppearance
                                    }
                                }
                                isExpanded = false
                            }) {
                            Text(
                                text = label,
                                color =
                                if (viewModel.dDateFormat.isNullOrEmpty() && label == selectedDateFormat
                                    || viewModel.dAppearance.isNullOrEmpty() && label == selectedAppearance) {
                                    Color.Blue
                                } else if (
                                    label == viewModel.dDateFormat
                                    || label == viewModel.dAppearance
                                )
                                    Color.Blue else
                                    Color.Black
                            )
                        }
                    }
                }
                "newPage" -> {
                    when (nameOfMenu) {
                        "editor" -> {
                            DropdownMenuItem(onClick = {
                                viewModelNewPage.dEditor = "markdown"
                                isExpanded = false
                            }) {
                                Text(text = "markdown")
                            }
                            DropdownMenuItem(onClick = {
                                viewModelNewPage.dEditor = "html"
                                isExpanded = false
                            }) {
                                Text(text = "html")
                            }
                        }
                        "language" -> {
                            DropdownMenuItem(onClick = {
                                viewModelNewPage.dLanguage = "ru"
                                isExpanded = false
                            }) {
                                Text(text = "ru")
                            }
                        }
                    }
                } else -> {}
            }

        }
    }
}