import kotlinx.browser.document
import org.jetbrains.compose.web.dom.*

fun main() {
    renderComposable(rootElementId = "root") {
        Div() {
            Text("Alan")
        }
    }
}