package com.example.kmmexample.web

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

    memScoped {
        val argc = args.size + 1
        val argv = (arrayOf("skikoApp") + args).map { it.cstr.ptr }.toCValues()
        autoreleasepool {
            UIApplicationMain(argc, argv, null, NSStringFromClass(SkikoAppDelegate))
        }
    }
}

class SkikoAppDelegate : UIResponder, UIApplicationDelegateProtocol {
    companion object : UIResponderMeta(), UIApplicationDelegateProtocolMeta

    @ObjCObjectBase.OverrideInit
    constructor() : super()

    private var _window: UIWindow? = null
    override fun window() = _window
    override fun setWindow(window: UIWindow?) {
        _window = window
    }

    override fun application(application: UIApplication, didFinishLaunchingWithOptions: Map<Any?, *>?): Boolean {
        val repo = koin.get<PeopleInSpaceRepositoryInterface>()

        window = UIWindow(frame = UIScreen.mainScreen.bounds)
        window!!.rootViewController = Application("KmmExample") {
            Column {
                // To skip upper part of screen.
                Box(modifier = Modifier.height(48.dp))
                PersonListScreen(repo)
            }
        }
        window!!.makeKeyAndVisible()
        return true
    }
}

@Composable
fun mainScreen() {
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