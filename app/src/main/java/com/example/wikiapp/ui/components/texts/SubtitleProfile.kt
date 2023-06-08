package com.example.wikiapp.ui.components.texts

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.sp
import com.example.wikiapp.ui.theme.darkGray

@Composable
fun SubtitleProfile(subtitle: String) {
    Text(
        text = subtitle,
        fontSize = 10.sp,
        color = darkGray
    )
}