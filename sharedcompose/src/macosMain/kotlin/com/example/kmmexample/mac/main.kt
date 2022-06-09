package com.example.kmmexample.mac

import androidx.compose.ui.window.Window
import com.example.kmmexample.common.mainScreen
import platform.AppKit.NSApp
import platform.AppKit.NSApplication

// ./gradlew runDebugExecutableMacosX64
fun main() {
    NSApplication.sharedApplication()
    Window("Kmm Example") {
        mainScreen()
    }
    NSApp?.run()
}