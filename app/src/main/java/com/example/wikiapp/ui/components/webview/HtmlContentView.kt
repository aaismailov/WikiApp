package com.example.wikiapp.ui.components.webview

import android.content.res.Configuration
import android.os.Build
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.annotation.RequiresApi
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.webkit.WebSettingsCompat
import androidx.webkit.WebViewFeature
import androidx.webkit.WebViewFeature.isFeatureSupported
import com.example.wikiapp.ui.screens.home.HomeViewModel
import com.example.wikiapp.utils.SuccessResult
import com.example.wikiapp.utils.getIdFromUrl


@Composable
fun HtmlContentView(
    content: String,
    doOnFinished: () -> Unit
) {
    val viewModel: HomeViewModel = viewModel()
    val getPageListResult by viewModel.getPageListResult.collectAsState()

    val uriHandler = LocalUriHandler.current
    var enableUriHandler by remember { mutableStateOf(false) }

    AndroidView(
        factory = {
            WebView(it).apply {
                settings.javaScriptEnabled = true
                settings.userAgentString = settings.userAgentString.replace("; wv)", ")")
                webViewClient = object : WebViewClient() {

                    override fun shouldInterceptRequest(
                        view: WebView,
                        request: WebResourceRequest
                    ): WebResourceResponse? {
                        val url = request.url
                        if (url.host?.contains("wiki.miem.hse.ru") == true) {
                            getPageListResult.also { result ->
                                if (result is SuccessResult) {
                                    if (url.toString() == "data:text/HTML;charset=utf-8;base64,") {
                                        enableUriHandler = true
                                    }
                                    val id = getIdFromUrl(url.toString(), getPageListResult.data?.data?.pages?.list!!)
                                    doOnFinished()
                                    if (id != 0) {
                                        viewModel.getSinglePage(id)
                                    }
                                }
                            }
                        } else if (enableUriHandler) { // Open in browser if it's an external link
                            uriHandler.openUri(url.toString())
                        }
                        return super.shouldInterceptRequest(view, request)
                    }
                }
                if ((resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES) {
                    if (isFeatureSupported(WebViewFeature.ALGORITHMIC_DARKENING)) {
                        WebSettingsCompat.setAlgorithmicDarkeningAllowed(settings, true)
                    } else if (isFeatureSupported(WebViewFeature.FORCE_DARK)) {
                        @Suppress("DEPRECATION")
                        WebSettingsCompat.setForceDark(settings, WebSettingsCompat.FORCE_DARK_ON)
                    }
                }
                loadDataWithBaseURL(
                    null, content,
                    "text/HTML", "UTF-8", null
                )
            }
        }, update = {
            it.loadDataWithBaseURL(
                null, content,
                "text/HTML", "UTF-8", null
            )
        }
    )
}