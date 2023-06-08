package com.example.wikiapp.ui.components.dialogs

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.Surface
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.wikiapp.R
import com.example.wikiapp.ui.components.dropdownmenu.DropDownMenu
import com.example.wikiapp.ui.components.rows.HeaderForDialogRow
import com.example.wikiapp.ui.components.textinput.CustomTextInput
import com.example.wikiapp.ui.screens.newpage.NewPageViewModel
import com.example.wikiapp.ui.theme.ApolloSampleTheme
import com.example.wikiapp.ui.theme.darkBlueColor

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SettingsOfPageDialog(
    setShowDialog: (Boolean) -> Unit
) {
    val scrollStateVer = rememberScrollState()
    val viewModel: NewPageViewModel = viewModel()

    val titleEditor = "Редактор"
    val titleHeader = "Заголовок"
    val titleDescription = "Краткое описание"
    val titleLanguage = "Язык"
    val titlePath = "Путь"
    val titleTags = "Теги"
    val titleCategory = "Категоризация"
    val titleParams = "Параметры страницы"
    val titleInfo = "Информация о странице"
    val titleDesc = "Текст под заголовком"
    val titlePublished = "Статус публикации"
    val titlePrivate = "Приватность"
    val titlePathExample = "Например: test/test/page"
    val titleUnderTags =
        "Используйте теги (через запятую) для классификации страниц и упрощения их поиска. Например: " +
                "учеба, онлайн-трансляции"

    val isPublished = remember { mutableStateOf(true) }
    val isPrivate = remember { mutableStateOf(false) }
    val textColor = remember { mutableStateOf(Color.Unspecified) }

    Dialog(onDismissRequest = { setShowDialog(false) }) {
        ApolloSampleTheme(darkTheme = false) {
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = Color.White,
                modifier = Modifier
                    .width(250.dp)
                    .height(500.dp)
                    .padding(start = 5.dp, end = 5.dp),
            ) {
                Box(
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        modifier = Modifier
                            .padding(10.dp)
                            .verticalScroll(scrollStateVer),
                        horizontalAlignment = Alignment.Start
                    ) {
                        HeaderForDialogRow(
                            imageId = R.drawable.cog,
                            colorImage = darkBlueColor,
                            title = titleParams
                        ) { setShowDialog(false) }
                        Text(
                            text = titleEditor,
                            color = Color.Gray,
                            fontSize = 14.sp
                        )
                        DropDownMenu(title = "", nameOfScreen = "newPage", nameOfMenu = "editor")
                        Spacer(modifier = Modifier.height(10.dp))
                        Divider()

                        Text(
                            text = titleInfo,
                            color = Color.Gray,
                            fontSize = 14.sp
                        )
                        CustomTextInput(
                            value = viewModel.dTitle,
                            onValueChange = { viewModel.dTitle = it },
                            label = titleHeader,
                            hint = "",
                            isError = viewModel.dTitle.isEmpty()
                        )
                        CustomTextInput(
                            value = viewModel.dDescription,
                            onValueChange = { viewModel.dDescription = it },
                            label = titleDescription,
                            hint = "",
                            isError = false
                        )
                        Text(
                            text = titleDesc,
                            color = Color.Gray,
                            fontSize = 12.sp
                        )
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(titlePublished, fontSize = 14.sp, color = textColor.value)
                            Switch(
                                checked = isPublished.value,
                                onCheckedChange = {
                                    isPublished.value = it
                                    viewModel.dIsPublished = isPublished.value
                                }
                            )
                        }
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(titlePrivate, fontSize = 14.sp, color = textColor.value)
                            Switch(
                                checked = isPrivate.value,
                                onCheckedChange = {
                                    isPrivate.value = it
                                    viewModel.dIsPrivate = isPrivate.value
                                }
                            )
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                        Divider()

                        Text(
                            text = titleLanguage,
                            color = Color.Gray,
                            fontSize = 14.sp
                        )
                        DropDownMenu(title = "", nameOfScreen = "newPage", nameOfMenu = "language")
                        Text(
                            text = titlePath,
                            color = Color.Gray,
                            fontSize = 14.sp
                        )
                        CustomTextInput(
                            value = viewModel.dPath,
                            onValueChange = { viewModel.dPath = it },
                            label = titlePath,
                            hint = "",
                            isError = viewModel.dPath.isEmpty()
                        )
                        Text(
                            text = titlePathExample,
                            color = Color.Gray,
                            fontSize = 12.sp
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Divider()

                        Text(
                            text = titleCategory,
                            color = Color.Gray,
                            fontSize = 14.sp
                        )
                        CustomTextInput(
                            value = viewModel.dTags,
                            onValueChange = { viewModel.dTags = it },
                            label = titleTags,
                            hint = "",
                            isError = false
                        )
                        Text(
                            text = titleUnderTags,
                            color = Color.Gray,
                            fontSize = 12.sp
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                    }
                }
            }
        }
    }
}