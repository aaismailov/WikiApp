package com.example.wikiapp.ui.components.pager.item

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wikiapp.ui.theme.middleGray

@Composable
fun ContentRowWithoutImage(title: String, subtitle: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp)
            .background(Color.White)
            .padding(horizontal = 10.dp, vertical = 7.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {

            Column {
                Text(
                    text = title,
                    fontSize = 10.sp,
                    color = Color.LightGray
                )

                Text(
                    text = subtitle,
                    fontSize = 10.sp,
                    color = middleGray,
                    fontWeight = FontWeight.Bold
                )
            }
        }

    }
}
