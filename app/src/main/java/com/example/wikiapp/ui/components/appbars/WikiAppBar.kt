package com.example.wikiapp.ui.components.appbars

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.wikiapp.R
import com.example.wikiapp.ui.screens.main.Routes

@Composable
fun WikiAppBar(
    text: String = "МИЭМ Wiki",
    userName: String = "Фамилия Имя Отчество",
    userEmail: String = "xxxx@mail.ru",
    logoutOnClick: () -> Unit,
    navController: NavController
) {
    var showMenu by remember { mutableStateOf(false) }

    TopAppBar(
        title = {
            Text(
                text = text,
                modifier = Modifier.clickable(
                    onClick = {
                        navController.navigate("${Routes.home}/0")
                    }
                )
            )
        },
        actions = {
            IconButton(onClick = { navController.navigate(Routes.search) }) {
                Icon(
                    Icons.Filled.Search,
                    contentDescription = null,
                    tint = Color.White
                )
            }

            IconButton(onClick = { navController.navigate(Routes.favourite) }) {
                Icon(
                    painter = painterResource(R.drawable.star),
                    contentDescription = null,
                    modifier = Modifier.size(20.dp),
                    tint = Color.White
                )
            }

            IconButton(onClick = { navController.navigate(Routes.newPage) }) {
                Icon(
                    painter = painterResource(R.drawable.text_box_plus_outline),
                    contentDescription = null,
                    tint = Color.White
                )
            }

            IconButton(onClick = { showMenu = !showMenu }) {
                Icon(
                    Icons.Filled.AccountCircle,
                    contentDescription = "Учётная запись",
                    tint = Color.White
                )
            }
            DropdownMenu(
                expanded = showMenu,
                onDismissRequest = { showMenu = false }
            ) {
                DropdownMenuItem(onClick = {}) {
                    Row(
                        modifier = Modifier.padding(vertical = 10.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .padding(5.dp)
                                .background(Color.Blue, shape = CircleShape)
                                .padding(8.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = if (userName.isNotEmpty()) "${
                                    userName.split(" ").first()[0].uppercase()
                                }${userName.split(" ").last()[0].uppercase()}" else "",
                                color = Color.White,
                                textAlign = TextAlign.Center
                            )
                        }
                        Column {
                            Text(
                                userName,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                userEmail
                            )
                        }
                    }
                }
                DropdownMenuItem(onClick = {
                    showMenu = true
                    navController.navigate(Routes.profile)
                }) {
                    Row {
                        Icon(
                            Icons.Filled.Person,
                            contentDescription = null
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = "Профиль"
                        )
                    }
                }
                DropdownMenuItem(onClick = {
                    showMenu = false
                    logoutOnClick()
                }) {
                    Row {
                        Icon(
                            Icons.Filled.ExitToApp,
                            contentDescription = "exit",
                            tint = Color.Red
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = "Выход",
                            color = Color.Red
                        )
                    }
                }
            }
        },
        backgroundColor = Color.Black,
        contentColor = Color.White,
        elevation = 12.dp
    )
}