package com.example.wikiapp.ui.components.texts

import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ScreenTitle(
    text: String
) = Text(
    text = text,
    modifier = Modifier.padding(start = 8.dp, top = 15.dp),
    style = MaterialTheme.typography.h5
)