package com.example.wikiapp.ui.components.pager.item

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wikiapp.ui.components.button.EditButton
import com.example.wikiapp.ui.components.texts.SubtitleProfile


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ContentRowWithImage(idImg: Int, title: String, subtitle: String, isBtn: Boolean) {
    val isButtonVisible by remember { mutableStateOf(isBtn) }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp)
            .background(Color.White)
            .padding(horizontal = 5.dp, vertical = 2.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {

            Image(
                painter = painterResource(id = idImg),
                contentDescription = "",
                colorFilter = ColorFilter.tint(Color.Gray),
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .height(20.dp)
                    .width(20.dp)
                    .padding(start = 5.dp, end = 5.dp)
            )

            Column {
                Text(
                    text = title,
                    color = Color.Black,
                    fontSize = 10.sp,
                )
                if (subtitle.isNotEmpty()) {
                    SubtitleProfile(subtitle)
                }

            }
        }
        if (isButtonVisible) {
            EditButton(title = title, subtitle = subtitle)
        }
    }
}
