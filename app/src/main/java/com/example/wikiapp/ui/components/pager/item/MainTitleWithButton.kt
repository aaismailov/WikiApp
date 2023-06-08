package com.example.wikiapp.ui.components.pager.item

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wikiapp.ui.components.button.SaveButton
import com.example.wikiapp.ui.theme.greyText

@Composable
fun MainTitleWithButton(idImg: Int, title: String, isButtonSave: Boolean) {
    val isButtonSave by remember { mutableStateOf(isButtonSave) }
    Row(
        modifier = Modifier
            .padding(horizontal = 3.dp, vertical = 3.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        Text(
            text = title,
            color = greyText,
            fontSize = 12.sp
        )
        if (isButtonSave)
            SaveButton()
    }
}