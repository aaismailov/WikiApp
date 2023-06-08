package com.example.wikiapp.ui.components.appbars

import android.util.Log
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.example.wikiapp.R
import kotlin.math.log

@Composable
fun WikiBottomBar() {

    var bottomState by remember {
        mutableStateOf("Create")
    }

    BottomNavigation(backgroundColor = Color.Black) {
        BottomNavigationItem(
            selected = bottomState == "Close",
            onClick = {
                Log.d("@@", "WikiBottomBar: close")
                bottomState = "Close"},
            icon = {
                Icon(imageVector = Icons.Default.Close,"Close",
                    tint = Color.Red)
            },
            label = { Text(text = "Закрыть", color = Color.White) }
        )
        BottomNavigationItem(
            selected = bottomState == "Settings",
            onClick = { bottomState = "Settings"
                Log.d("@@", "WikiBottomBar: Settings")},
            icon = {
                Icon(imageVector = Icons.Default.Settings,"Settings",
                    tint = Color.Blue)
            },
            label = { Text(text = "Свойства", color = Color.White) }
        )
        BottomNavigationItem(
            selected = bottomState == "Create",
            onClick = { bottomState = "Create"

                Log.d("@@", "WikiBottomBar: Create")},
            icon = {
                Icon(painter = painterResource(R.drawable.check),"Create",
                    tint = Color.Green
                )
            },
            label = { Text(text = "Создать", color = Color.White) }
        )

    }

}