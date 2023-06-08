package com.example.wikiapp.ui.components.breadcrumb

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.wikiapp.PagesListQuery
import com.example.wikiapp.utils.getIdFromUrl

@Composable
fun BreadCrumb(
    pagePath: List<String>?,
    pageLists: List<PagesListQuery.List>,
    getSinglePage: (id: Int) -> Unit
) = LazyRow(
    verticalAlignment = Alignment.CenterVertically
) {
    item {
        IconButton(
            onClick = { getSinglePage(0) }
        ) {
            Icon(
                Icons.Filled.Home,
                contentDescription = null,
                tint = Color.Gray
            )
        }
    }

    pagePath?.let {
        items(it.size) { index ->
            var path = ""
            for (i in 0..index) path += "/${pagePath[i]}"

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "/",
                    modifier = Modifier.padding(horizontal = 10.dp),
                    color = Color.Gray
                )
                TextButton(
                    onClick = {
                        val id = getIdFromUrl(path, pageLists)
                        if (id != 0) getSinglePage(id)
                    },
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = if (isSystemInDarkTheme()) Color.White else Color.Black
                    )
                ) {
                    Text(pagePath[index])
                }
            }
        }
    }
}