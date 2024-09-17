import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.webkit.JavascriptInterface
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView

actual fun getPlatformName(): String = "Android"

@Composable fun MainView() = App()

class WebAppInterface(private val mContext: Context) {
    @JavascriptInterface
    fun showToast(toast: String) {
        Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show()
    }
}

@Composable
actual fun MyWebView(
    htmlContent: String,
    isLoading: (isLoading: Boolean) -> Unit,
    onUrlClicked: (url: String) -> Unit
) {
    var isLoadingFinished by remember { mutableStateOf(false) }
    Box(modifier = Modifier.fillMaxWidth().verticalScroll(rememberScrollState())) {
        AndroidView(
            modifier = Modifier.fillMaxWidth(),
            factory = {
                WebView(it).apply {
                    scrollBarStyle = View.SCROLLBARS_OUTSIDE_OVERLAY
                    setBackgroundColor(Color.TRANSPARENT)
                    layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                    )
                    settings.javaScriptEnabled = true
                    addJavascriptInterface(WebAppInterface(it), "FLMB");
                    settings.apply {
                        loadWithOverviewMode = true
                        useWideViewPort = true
                        layoutAlgorithm = WebSettings.LayoutAlgorithm.NORMAL
                    }
                    webViewClient = object : WebViewClient() {
                        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                            isLoading(true)
                        }

                        override fun onPageFinished(view: WebView?, url: String?) {
                            isLoadingFinished = true
                            isLoading(false)
                        }

                        override fun shouldOverrideUrlLoading(
                            view: WebView?,
                            request: WebResourceRequest?
                        ): Boolean {
                            return if (request?.url.toString().contains("jpg") ||
                                request?.url.toString().contains("png") ||
                                request?.url.toString().contains("attachment_id")) {
                                true
                            } else {
                                onUrlClicked(request?.url.toString())
                                false
                            }
                        }
                    }
                }
            },
            update = {
                it.loadUrl("http://192.168.12.161:5173/")
            }
        )
    }
}