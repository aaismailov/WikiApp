package com.example.wikiapp.ui.components.button

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

@Composable
fun ButtonWithImageText(
    onClick: () -> Unit,
    colorOfBorder: Color,
    backgroundColor: Color,
    imageId: Int,
    colorOfImage: Color,
    paddingEndOfImg: Dp,
    textOfBtn: String,
    colorOfText: Color,
    fontSize: TextUnit,
    fontWeight: FontWeight,
    contentDescription: String
) =
    Button(
        onClick = onClick,
        border = BorderStroke(0.8.dp, colorOfBorder),
        colors = ButtonDefaults.buttonColors(backgroundColor = backgroundColor)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(imageId),
                contentDescription = contentDescription,
                colorFilter = ColorFilter.tint(colorOfImage),
                modifier = Modifier
                    .padding(end = paddingEndOfImg)
                    .width(18.dp)
                    .height(18.dp)
            )
            Text(
                text = textOfBtn,
                color = colorOfText,
                fontSize = fontSize,
                fontWeight = fontWeight
            )
        }
    }
