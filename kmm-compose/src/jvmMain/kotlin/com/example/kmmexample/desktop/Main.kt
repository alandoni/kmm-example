package com.example.kmmexample.desktop

import androidx.compose.runtime.Composable
import androidx.compose.ui.window.ApplicationScope
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.example.kmmexample.di.KoinInit
import com.example.kmmexample.mainScreen

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

    Window(
        onCloseRequest = applicationScope::exitApplication,
        state = windowState,
        title = "Kmm Example"
    ) {
        mainScreen()
    }
}