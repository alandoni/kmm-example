package com.example.kmmexample

import platform.UIKit.UIDevice

actual fun getPlatform() = Platform.iOS

val Platform.getSystemName: String
    get() {
        return "${UIDevice.currentDevice.systemName()} ${UIDevice.currentDevice.systemVersion}"
    }
