package com.example.wikiapp.ui.screens.wifi

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wikiapp.R
import com.example.wikiapp.ui.components.image.LogoImage
import com.example.wikiapp.ui.components.loader.CirculaLoaderForWifi
import com.example.wikiapp.ui.theme.orange

@Composable
fun WifiConnection() {
    val titleConnectionOff = "Отсутствует подключение к сети"
    val titleWiki = "Wiki"

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(30.dp)
                .background(Color.Red),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(R.drawable.ic_wifi_off_24),
                contentDescription = "wifi_off",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(24.dp)
                    .width(24.dp)
                    .padding(end = 10.dp)
            )
            Text(
                text = titleConnectionOff,
                color = Color.White
            )
        }
        Row {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color.Yellow,
                                Color.White,
                                orange
                            )
                        )
                    ),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                Row(
                    modifier = Modifier
                        .height(55.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.Top
                ) {
                    LogoImage()

                    Spacer(modifier = Modifier.width(5.dp))

                    Text(
                        text = titleWiki,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                }
                CirculaLoaderForWifi()
            }
        }

    }
}
