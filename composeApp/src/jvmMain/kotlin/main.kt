import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import java.awt.Dimension
import org.scode.mynote.App
import org.scode.mynote.di.initKoin
import org.scode.mynote.ui.screen.detail.DetailScreen
import org.scode.mynote.ui.screen.history.HistoryScreen
import org.scode.mynote.view.ToastDialog

fun main() = application {
    Window(
        title = "MyNote",
        state = rememberWindowState(width = 400.dp, height = 550.dp),
        onCloseRequest = ::exitApplication,
        icon= painterResource("drawable/moon.svg")
    ) {
        initKoin()
        window.minimumSize = Dimension(350, 400)
        App()
    }
}

@Preview
@Composable
fun previewTest() {
    ToastDialog()
}