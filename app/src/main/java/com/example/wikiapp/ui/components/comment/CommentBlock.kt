package com.example.wikiapp.ui.components.comment

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wikiapp.CommentsListQuery
import com.example.wikiapp.R
import com.example.wikiapp.ui.theme.darkGray
import com.example.wikiapp.ui.theme.lightGray
import com.example.wikiapp.ui.theme.middleGray

@Composable
fun CommentBlock(
    userName: String = "User Name",
    userEmail: String = "User Email",
    commentsList: List<CommentsListQuery.List>,
    commentCreate: (content: String, name: String?, email: String?) -> Unit
) {
    var text by remember { mutableStateOf("") }
    val shape = RoundedCornerShape(7.dp)
    val underCommentText = "Оставьте первый комментарий."
    val focusManager = LocalFocusManager.current

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 5.dp)
            .clip(shape)
    ) {
        Box(
            modifier = Modifier
                .background(lightGray)
                .fillMaxWidth()
        ) {
            Column {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(middleGray)
                        .padding(4.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.message),
                        contentDescription = "comment",
                        colorFilter = ColorFilter.tint(Color.White),
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .height(24.dp)
                            .width(24.dp)
                            .padding(start = 3.dp)
                    )

                    Spacer(modifier = Modifier.width(7.dp))
                    Text(
                        text = "Комментарии",
                        fontSize = 16.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(70.dp)
                        .padding(7.dp)
                ) {
                    OutlinedTextField(
                        maxLines = 3,
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.White),
                        value = text,
                        placeholder = { Text(text = "Написать новый комментарий...") },
                        onValueChange = {
                            text = it
                        },
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = darkGray,
                            unfocusedBorderColor = middleGray,
                            cursorColor = Color.Black,
                            textColor = Color.Black,
                            placeholderColor = darkGray
                        )
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 7.dp, end = 7.dp, bottom = 2.dp),
                    verticalAlignment = Alignment.CenterVertically

                ) {
                    Row {
                        Text(
                            text = "Публикуется как ",
                            fontSize = 12.sp,
                            color = Color.Black
                        )
                        Text(
                            text = userName,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black,
                            modifier = Modifier.padding(end = 7.dp)
                        )
                    }
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 7.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            painter = painterResource(id = R.drawable.markdown) ,
                            contentDescription = "markdown",
                            colorFilter = ColorFilter.tint(middleGray),
                            contentScale = ContentScale.Fit,
                            modifier = Modifier
                                .padding(end = 3.dp)
                        )
                        Text(
                            text = "Формат Markdown",
                            fontSize = 10.sp,
                            color = middleGray
                        )
                    }

                    Button(
                        modifier = Modifier
                            .width(170.dp)
                            .height(30.dp),
                        colors = ButtonDefaults.buttonColors(backgroundColor = darkGray),
                        contentPadding = PaddingValues(
                            start = 5.dp,
                            top = 1.dp,
                            end = 5.dp,
                            bottom = 1.dp,
                        ),
                        onClick = {
                            commentCreate(text, userName, userEmail)
                            text = ""
                            focusManager.clearFocus()
                        },
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.message_btn) ,
                            contentDescription = "messageBtn",
                            colorFilter = ColorFilter.tint(Color.White),
                            contentScale = ContentScale.Fit,
                            modifier = Modifier
                                .height(16.dp)
                                .width(16.dp)
                                .padding(end = 3.dp)
                        )
                        Text(
                            text = "Оставьте комментарий",
                            fontSize = 10.sp,
                            color = Color.White,
                        )
                    }
                }

                Divider(
                    Modifier
                        .padding(horizontal = 7.dp, vertical = 10.dp),
                    color = middleGray,
                    thickness = 1.dp
                )

                if (commentsList.isEmpty()) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = underCommentText,
                            fontSize = 10.sp,
                            color = middleGray
                        )
                    }
                } else{
                    commentsList.forEach { item ->
                        Row(
                            modifier = Modifier.padding(10.dp)
                        ) {
                            Box(modifier = Modifier
                                .padding(5.dp)
                                .background(middleGray, shape = CircleShape)
                                .padding(8.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "${item.authorName.split(" ").first()[0].uppercase()}${item.authorName.split(" ").last()[0].uppercase()}",
                                    color = Color.White,
                                    textAlign = TextAlign.Center
                                )
                            }
                            Column {
                                Text(
                                    item.authorName,
                                    color = darkGray,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    item.content,
                                    color = Color.Gray
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}