package com.example.wikiapp.ui.components.edit

import android.R
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.wikiapp.ui.components.dropdownmenu.DropDownMenu
import com.example.wikiapp.ui.screens.profile.ProfileViewModel
import com.example.wikiapp.ui.theme.ApolloSampleTheme
import com.example.wikiapp.ui.theme.darkGray

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun EditDialog(
    title: String,
    subtitle: String,
    setShowTextEdit: (Boolean) -> Unit
) {
    val viewModel: ProfileViewModel = viewModel()

    val focusManager = LocalFocusManager.current

    Dialog(onDismissRequest = { setShowTextEdit(false) }) {
        ApolloSampleTheme(darkTheme = false) {
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = Color.White
            ) {
                Box(
                    contentAlignment = Alignment.Center
                ) {
                    Column(modifier = Modifier.padding(20.dp)) {

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Редактирование",
                                style = TextStyle(
                                    fontSize = 24.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "",
                                tint = colorResource(R.color.darker_gray),
                                modifier = Modifier
                                    .width(30.dp)
                                    .height(30.dp)
                                    .clickable { setShowTextEdit(false) }
                            )
                        }

                        Spacer(modifier = Modifier.height(20.dp))

                        if (title == "Отображаемое имя" || title == "Расположение"
                            || title == "Должность"
                        ) {
                            TextField(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .border(
                                        BorderStroke(
                                            width = 2.dp,
                                            color = colorResource(R.color.holo_blue_light),
                                        ),
                                        shape = RoundedCornerShape(50)
                                    ),
                                colors = TextFieldDefaults.textFieldColors(
                                    backgroundColor = Color.Transparent,
                                    focusedIndicatorColor = Color.Transparent,
                                    unfocusedIndicatorColor = Color.Transparent,
                                    textColor = Color.Black,
                                    placeholderColor = darkGray
                                ),
                                leadingIcon = {
                                    Icon(
                                        imageVector = Icons.Filled.Edit,
                                        contentDescription = "",
                                        modifier = Modifier
                                            .width(20.dp)
                                            .height(20.dp)
                                    )
                                },
                                placeholder = { Text(text = title) },
                                value = subtitle,
                                onValueChange = {
                                    when (title) {
                                        "Отображаемое имя" -> viewModel.dName = it
                                        "Расположение" -> viewModel.dLocation =
                                            if (it == "-") "" else it
                                        "Должность" -> viewModel.dJobTitle =
                                            if (it == "-") "" else it
                                    }
                                }
                            )
                        } else DropDownMenu(
                            title = title,
                            nameOfScreen = "profile",
                            nameOfMenu = ""
                        )

                        Spacer(modifier = Modifier.height(20.dp))

                        Box(modifier = Modifier.padding(40.dp, 0.dp, 40.dp, 0.dp)) {
                            Button(
                                onClick = {
                                    when (title) {
                                        "Отображаемое имя" -> viewModel.dName?.let {
                                            viewModel.updateName(
                                                it
                                            )
                                        }
                                        "Расположение" -> {
                                            if (viewModel.dLocation.isNullOrEmpty())
                                                viewModel.dLocation = "-"
                                            viewModel.updateLocation(viewModel.dLocation.toString())
                                        }
                                        "Должность" -> {
                                            if (viewModel.dJobTitle.isNullOrEmpty())
                                                viewModel.dJobTitle = "-"
                                            viewModel.updateJob(viewModel.dJobTitle)
                                        }
                                        "Часовой пояс" -> {
                                            viewModel.updateTimezone(viewModel.dTimezone)
                                        }
                                        "Формат даты" -> viewModel.updateDateFormat(viewModel.dDateFormat)
                                        "Внешний вид" -> viewModel.updateAppearance(viewModel.dAppearance)
                                    }
                                    setShowTextEdit(false)
                                    focusManager.clearFocus()
                                },
                                shape = RoundedCornerShape(50.dp),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(50.dp)
                            ) {
                                Text(text = "ОК")
                            }
                        }
                    }
                }
            }
        }
    }
}