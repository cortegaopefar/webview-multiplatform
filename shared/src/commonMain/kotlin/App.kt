import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalResourceApi::class)
@Composable
fun App() {
    MaterialTheme {
        var isLoadingDescription by remember { mutableStateOf(false) }
        Column {
            MyWebView(
                htmlContent = "",
                isLoading = { isLoadingDescription = it },
                onUrlClicked = { url: String -> "https://google.com" }
            )
        }
    }
}

@Composable
expect fun MyWebView(htmlContent: String, isLoading: (isLoading: Boolean) -> Unit, onUrlClicked: (url: String) -> Unit)

expect fun getPlatformName(): String