package com.example.kmmexample.desktop

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.window.ApplicationScope
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.example.kmmexample.data.models.RocketLaunch
import com.example.kmmexample.di.KoinInit
import com.example.kmmexample.viewmodel.MainViewModel
import com.example.kmmexample.viewmodel.MainViewModelState

fun main(args: Array<String>) {
    KoinInit.start()
    app()
}

fun app() = application {
    window(this)
}

@Composable
fun window(applicationScope: ApplicationScope) {
    val windowState = rememberWindowState()
    val viewModel = remember { MainViewModel() }

    val text by viewModel.text.collectAsState()
    val state by viewModel.state.collectAsState(initial = MainViewModelState.Loading)

    LaunchedEffect(true) {
        viewModel.getRocketLaunches()
    }

    Window(
        onCloseRequest = applicationScope::exitApplication,
        state = windowState,
        title = "Kmm Example"
    ) {
        Column(
            Modifier.fillMaxWidth()
        ) {
            BasicTextField(
                text,
                onValueChange = { s: String ->
                    viewModel.text.value = s
                },
                modifier = Modifier.fillMaxWidth().background(Color.Green),
            )
            Text(text = text.length.toString())
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
                rocketLaunchRow(it)
            }
        }
        is MainViewModelState.Error -> {
            Text(state.error.message ?: "Error")
        }
    }
}

@Composable
fun rocketLaunchRow(rocketLaunch: RocketLaunch) {
    Text(rocketLaunch.missionName)
}