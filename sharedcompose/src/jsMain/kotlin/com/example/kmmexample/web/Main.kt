package com.example.kmmexample.web

import androidx.compose.ui.window.Window
import com.example.kmmexample.common.mainScreen
//import com.example.kmmexample.di.KoinInit
import org.jetbrains.skiko.wasm.onWasmReady

// run with ./gradlew jsBrowserDevelopmentRun
fun main() {
    onWasmReady {
        Window("Kmm Example") {
            mainScreen()
        }
    }
}
