package com.example.wikiapp.ui.components.button

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun LoginButton(
    onClick: () -> Unit
) = OutlinedButton(
    onClick = onClick,
    modifier = Modifier
        .height(30.dp)
        .width(150.dp),
    border = BorderStroke(1.dp, Color.Gray),
    contentPadding = PaddingValues(0.dp),
    colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Black)
) {
    val titleLogIn = "Войти"
    Text(text = titleLogIn)
    Spacer(modifier = Modifier.width(15.dp))
    Icon(
        Icons.Default.ArrowForward,
        contentDescription = null,
        tint = Color.Black,
        modifier = Modifier
            .size(15.dp)
    )
}