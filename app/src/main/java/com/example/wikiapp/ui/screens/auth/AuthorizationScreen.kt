package com.example.wikiapp.ui.screens.auth

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.wikiapp.R
import com.example.wikiapp.ui.components.button.LoginButton
import com.example.wikiapp.ui.components.image.LogoImage
import com.example.wikiapp.ui.components.loader.CircularLoader
import com.example.wikiapp.ui.components.webview.CustomWebView
import com.example.wikiapp.ui.theme.orange
import com.example.wikiapp.utils.ErrorResult
import com.example.wikiapp.utils.LoadingResult
import com.example.wikiapp.utils.subscribeOnError
import kotlinx.coroutines.launch

@Composable
fun AuthorizationScreen(
    showMessage: (message: Int) -> Unit = {}
) {
    val viewModel: AuthViewModel = viewModel()
    val scope = LocalLifecycleOwner.current

    val location by viewModel.location.collectAsState()

    val authLoginResult by viewModel.authLoginResult.collectAsState()
    authLoginResult.subscribeOnError(showMessage)

    var showWebView by remember { mutableStateOf(false) }

    if (authLoginResult is LoadingResult) CircularLoader()

    if (showWebView) {
        CustomWebView(
            location = location,
            getToken = viewModel::changeToken,
            doOnFinished = { showWebView = false }
        )
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.Yellow,
                            Color.White,
                            orange
                        )
                    )
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Row(
                modifier = Modifier
                    .height(55.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.Top
            ) {
                LogoImage()

                Spacer(modifier = Modifier.width(5.dp))

                Text(
                    text = "Wiki",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
            }

            Text(
                text = "Сервис хранения документации",
                fontSize = 15.sp,
                color = Color.LightGray,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(15.dp))

            LoginButton {
                scope.lifecycleScope.launch {
                    viewModel.changeLocation("")
                    viewModel.authLogin()
                    showWebView = true
                }
            }
        }
    }
}