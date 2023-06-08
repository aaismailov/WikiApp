package com.example.wikiapp.ui.components.dropdownmenu

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wikiapp.R

@Composable
fun DropDownItem(
    onClick: () -> Unit,
    imageId: Int,
    imageWidth: Dp,
    imageHeight: Dp,
    colorOfImage: Color,
    textOfItem: String
) =
    IconButton(onClick = onClick) {
        Row(
            modifier = Modifier
                .padding(start = 7.dp, end = 7.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Icon(
                painter = painterResource(imageId),
                contentDescription = textOfItem,
                tint = colorOfImage,
                modifier = Modifier
                    .padding(end = 7.dp)
                    .width(imageWidth)
                    .height(imageHeight)
            )
            Text(
                text = textOfItem,
                fontSize = 14.sp
            )
        }
    }