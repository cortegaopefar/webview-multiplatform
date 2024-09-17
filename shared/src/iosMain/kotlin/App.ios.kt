import androidx.compose.runtime.Composable

@Composable
actual fun MyWebView(
    htmlContent: String,
    isLoading: (isLoading: Boolean) -> Unit,
    onUrlClicked: (url: String) -> Unit
) {
}
