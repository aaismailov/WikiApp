package com.example.wikiapp.ui.components.fabs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.wikiapp.R
import com.example.wikiapp.ui.theme.blueColor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun DrawerFAB(scope: CoroutineScope, scaffoldState: ScaffoldState) {
    Column(
        modifier = Modifier
            .padding(start = 40.dp),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.Start
    ) {
        FloatingActionButton(
            onClick = {
                scope.launch {
                    scaffoldState.drawerState.open()
                }
            },
            backgroundColor = blueColor,
            contentColor = Color.White
        ) {
            Icon(
                painterResource(id = R.drawable.ic_burger),
                ""
            )
        }
    }
}