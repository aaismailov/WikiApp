package com.example.wikiapp.ui.components.button

import android.util.Log
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.sp

@Composable
fun CreateNewPageButton(
    createNewPage: () -> Unit
) {
    val focusManager = LocalFocusManager.current
    Button(
        onClick = {
            createNewPage()
            Log.d("NEW_PAGE", "Page is created")
            focusManager.clearFocus()
        }
    ) {
        Text(
            text = "Create",
            fontSize = 10.sp,
            color = Color.White,
        )
    }


}
