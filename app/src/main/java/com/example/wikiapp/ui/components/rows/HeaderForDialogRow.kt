package com.example.wikiapp.ui.components.rows

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wikiapp.R
import com.example.wikiapp.ui.theme.darkBlueColor

@Composable
fun HeaderForDialogRow(
    imageId: Int,
    colorImage: Color,
    title: String,
    setShowDialog: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(imageId),
            contentDescription = "info",
            colorFilter = ColorFilter.tint(colorImage),
            modifier = Modifier
                .width(24.dp)
                .height(24.dp)
                .padding(end = 5.dp)
        )
        Text(
            text = title,
            style = TextStyle(
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        )
        Icon(
            imageVector = Icons.Default.Close,
            contentDescription = "close",
            tint = colorResource(android.R.color.darker_gray),
            modifier = Modifier
                .width(24.dp)
                .height(24.dp)
                .padding(start = 5.dp)
                .clickable { setShowDialog(false) }
        )
    }
}