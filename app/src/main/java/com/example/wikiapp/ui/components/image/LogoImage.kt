package com.example.wikiapp.ui.components.image

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.wikiapp.R

@Composable
fun LogoImage(
    painter: Painter = painterResource(R.drawable.miem_gray)
) = Image(
    painter = painter,
    contentDescription = "logo",
    contentScale = ContentScale.Crop,
    modifier = Modifier
        .height(50.dp)
        .width(200.dp)
)