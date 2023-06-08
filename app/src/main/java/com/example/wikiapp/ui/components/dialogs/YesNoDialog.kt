package com.example.wikiapp.ui.components.dialogs

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.wikiapp.R
import com.example.wikiapp.ui.components.button.ButtonWithImageText
import com.example.wikiapp.ui.components.rows.HeaderForDialogRow
import com.example.wikiapp.ui.theme.ApolloSampleTheme
import com.example.wikiapp.ui.theme.darkBlueColor
import com.example.wikiapp.ui.theme.darkRedColor

@Composable
fun YesNoDialog(
    title: String,
    subTitle: String,
    setShowDialog: (Boolean) -> Unit, onClick: () -> Unit
) {
    val titleYes = "Да"
    val titleNo = "Нет"

    Dialog(onDismissRequest = { setShowDialog(false) }) {
        ApolloSampleTheme(darkTheme = false) {
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = Color.White,
                modifier = Modifier
                    .width(250.dp)
                    .height(200.dp)
                    .padding(start = 5.dp, end = 5.dp)
            ) {
                Box(
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        modifier = Modifier.padding(15.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        HeaderForDialogRow(
                            imageId = R.drawable.alert,
                            colorImage = darkRedColor,
                            title = title
                        ) { setShowDialog(false) }

                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            modifier = Modifier.padding(5.dp),
                            text = subTitle,
                            fontSize = 14.sp
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            ButtonWithImageText(
                                onClick = {
                                    setShowDialog(false)
                                },
                                colorOfBorder = darkBlueColor,
                                backgroundColor = Color.White,
                                imageId = R.drawable.close_circle_outline,
                                colorOfImage = darkBlueColor,
                                paddingEndOfImg = 5.dp,
                                textOfBtn = titleNo.uppercase(),
                                colorOfText = darkBlueColor,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Normal,
                                contentDescription = "no"
                            )

                            ButtonWithImageText(
                                onClick = onClick,
                                colorOfBorder = darkBlueColor,
                                backgroundColor = darkBlueColor,
                                imageId = R.drawable.check,
                                colorOfImage = Color.White,
                                paddingEndOfImg = 5.dp,
                                textOfBtn = titleYes.uppercase(),
                                colorOfText = Color.White,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Normal,
                                contentDescription = "yes"
                            )
                        }
                    }
                }
            }
        }
    }
}