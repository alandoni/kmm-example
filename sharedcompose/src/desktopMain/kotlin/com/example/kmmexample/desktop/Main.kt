package com.example.kmmexample.desktop

import androidx.compose.runtime.*
import androidx.compose.ui.window.ApplicationScope
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.example.kmmexample.common.mainScreen

fun main(args: Array<String>) {
    app()
}

fun app() = application {
    window(this)
}

@Composable
fun window(applicationScope: ApplicationScope) {
    val windowState = rememberWindowState()

    Window(
        onCloseRequest = applicationScope::exitApplication,
        state = windowState,
        title = "Kmm Example"
    ) {
        mainScreen()
    }
}
