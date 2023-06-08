package com.example.wikiapp.ui.components.button

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wikiapp.R
import com.example.wikiapp.ui.theme.greenBtnSave

@Composable
fun SaveButton() {
    val titleSave = "Сохранить"
    Button(
        onClick = {

        },
        colors = ButtonDefaults.buttonColors(backgroundColor = greenBtnSave)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(R.drawable.check),
                contentDescription = null,
                colorFilter = ColorFilter.tint(Color.White),
                modifier = Modifier
                    .padding(end = 10.dp)
                    .width(18.dp)
                    .height(18.dp)
            )
            Text(
                text = titleSave.uppercase(),
                color = Color.White,
                textAlign = TextAlign.Center,
                fontSize = 10.sp
            )
        }
    }
}
