package com.example.wikiapp.ui.components.button

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wikiapp.R
import com.example.wikiapp.ui.components.edit.EditDialog

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun EditButton(title: String, subtitle: String) {
    val showDialog = remember { mutableStateOf(false) }
    val titleEdit = "Редактировать"

    if (showDialog.value)
        EditDialog(title = title, subtitle = subtitle,
            setShowTextEdit = {
                showDialog.value = it
            }
        )

    TextButton(
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
        onClick = {
            showDialog.value = true
        }
    ) {
        Image(
            painter = painterResource(id = R.drawable.pencil),
            contentDescription = "editBtn",
            colorFilter = ColorFilter.tint(Color.Gray),
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .height(16.dp)
                .width(16.dp)
                .padding(end = 3.dp)
        )
        Text(
            text = titleEdit.uppercase(),
            color = Color.Gray,
            fontSize = 8.sp,
            fontWeight = FontWeight.Bold
        )
    }
}


