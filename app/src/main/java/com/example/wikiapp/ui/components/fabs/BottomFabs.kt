package com.example.wikiapp.ui.components.fabs

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import kotlinx.coroutines.CoroutineScope

@Composable
fun BottomFabs(scope: CoroutineScope, scaffoldState: ScaffoldState, scrollState: ScrollState) {
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        DrawerFAB(scope = scope, scaffoldState = scaffoldState)
        ScrollToTopFab(scrollState = scrollState)
    }
}