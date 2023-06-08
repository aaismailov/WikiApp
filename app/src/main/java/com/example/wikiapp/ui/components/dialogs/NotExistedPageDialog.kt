package com.example.wikiapp.ui.components.dialogs

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.example.wikiapp.R
import com.example.wikiapp.ui.components.button.ButtonWithImageText
import com.example.wikiapp.ui.screens.main.Routes
import com.example.wikiapp.ui.screens.newpage.NewPage
import com.example.wikiapp.ui.theme.ApolloSampleTheme
import com.example.wikiapp.ui.theme.darkBlueColor

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NotExistedPageDialog(
   navController: NavController,
    setShowDialog: (Boolean) -> Unit) {

    val titlePageNotExisted = "Эта страница ещё не существует"
    val titleCreatePageNow = "Создать страницу прямо сейчас?"
    val titleBtnCreatePage = "Создать страницу"
    val titleBackBtn = "Назад"

    Dialog(onDismissRequest = { setShowDialog(false) }) {
        ApolloSampleTheme(darkTheme = false) {
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = Color.White
            ) {
                Box(
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        modifier = Modifier.padding(15.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painter = painterResource(R.drawable.text_box_remove_outline),
                                contentDescription = "notExistedPage",
                                colorFilter = ColorFilter.tint(darkBlueColor),
                                modifier = Modifier
                                    .width(34.dp)
                                    .height(34.dp)
                                    .padding(end = 5.dp)
                            )
                            Text(
                                text = titlePageNotExisted,
                                style = TextStyle(
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Text(
                            text = titleCreatePageNow,
                            style = TextStyle(
                                fontSize = 14.sp,
                                color = Color.Black
                            )
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        ButtonWithImageText(
                            onClick = {
                                navController.navigate(Routes.newPage)
                            },
                            colorOfBorder = darkBlueColor,
                            backgroundColor = Color.White,
                            imageId = R.drawable.plus,
                            colorOfImage = darkBlueColor,
                            paddingEndOfImg = 5.dp,
                            textOfBtn = titleBtnCreatePage.uppercase(),
                            colorOfText = darkBlueColor,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Normal,
                            contentDescription = "create new page screen"
                        )

                        ButtonWithImageText(
                            onClick = { setShowDialog(false) },
                            colorOfBorder = darkBlueColor,
                            backgroundColor = darkBlueColor,
                            imageId = R.drawable.arrow_back,
                            colorOfImage = Color.White,
                            paddingEndOfImg = 5.dp,
                            textOfBtn = titleBackBtn.uppercase(),
                            colorOfText = Color.White,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Normal,
                            contentDescription = "back"
                        )
                    }
                }
            }
        }
    }
}