import androidx.compose.ui.window.ComposeUIViewController
import org.scode.mynote.App
import org.scode.mynote.di.initKoin
import platform.UIKit.UIViewController

fun MainViewController(): UIViewController = ComposeUIViewController {
    initKoin()
    App()
}
