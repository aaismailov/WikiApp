package com.example.wikiapp.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect

@Composable
inline fun Result<*>.subscribeOnError(crossinline onError: (message: Int) -> Unit) = (this as? ErrorResult)?.message?.let {
    LaunchedEffect(this) {
        onError(it)
    }
}