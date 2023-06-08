package com.example.wikiapp.ui.components.fabs

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.*
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.wikiapp.ui.theme.blueColor
import kotlinx.coroutines.launch

@Composable
fun ScrollToTopFab(scrollState: ScrollState) {
    val coroutineScope = rememberCoroutineScope()
    val fabAlpha: Float by animateFloatAsState(
        targetValue = if (scrollState.value > 0) 1f else 0f,
        animationSpec = tween(durationMillis = 300)
    )

    val fabOffsetY: Float by animateFloatAsState(
        targetValue = if (scrollState.value > 0) 0f else 48.dp.value,
        animationSpec = tween(durationMillis = 300)
    )
    Column(
        modifier = Modifier
            .padding(start = 40.dp),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.End
    ) {
        FloatingActionButton(
            modifier = Modifier
                .offset(y = fabOffsetY.dp)
                .alpha(fabAlpha),
            onClick = {
                coroutineScope.launch {
                    scrollState.animateScrollTo(0)
                }
            },
            backgroundColor = blueColor,
            contentColor = Color.White
        ) {
            Icon(
                imageVector = Icons.Default.KeyboardArrowUp,
                contentDescription = "Scroll to top",
                tint = Color.White
            )
        }
    }
}