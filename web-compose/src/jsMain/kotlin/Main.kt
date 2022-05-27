import androidx.compose.runtime.*
import com.example.kmmexample.data.models.RocketLaunch
import com.example.kmmexample.di.KoinInit
import com.example.kmmexample.viewmodel.MainViewModel
import com.example.kmmexample.viewmodel.MainViewModelState
import org.jetbrains.compose.web.attributes.InputType
import org.jetbrains.compose.web.css.background
import org.jetbrains.compose.web.dom.*
import org.jetbrains.compose.web.renderComposable

fun main() {
    KoinInit.start()

    renderComposable(rootElementId = "root") {
        val viewModel = remember { MainViewModel() }

        val text by viewModel.text.collectAsState()
        val state by viewModel.state.collectAsState(initial = MainViewModelState.Loading)

        LaunchedEffect(true) {
            console.log("Getting rocket launches")
            viewModel.getRocketLaunches()
        }

        Div {
            Input(
                type = InputType.Text,
                attrs = {
                    onInput { event ->
                        viewModel.text.value = event.value
                    }
                    style {
                        background("#00dd00")
                    }
                }
            )
        }
        Div {
            Text("${text.length}")
        }
        Div {
            rocketsList(state)
        }
    }
}

@Composable
fun rocketsList(state: MainViewModelState) {
    when (state) {
        is MainViewModelState.Loading ->
            Text("Loading")
        is MainViewModelState.Success -> {
            state.value.map {
                Div {
                    rocketLaunchRow(it)
                }
            }
        }
        is MainViewModelState.Error -> {
            Text(state.error.message ?: "Error")
        }
    }
}

@Composable
fun rocketLaunchRow(rocketLaunch: RocketLaunch) {
    console.log(rocketLaunch.missionName)
    Text(rocketLaunch.missionName)
}