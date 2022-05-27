package com.example.kmmexample

actual fun getPlatform() = Platform.Android

val Platform.getSystemName: String
    get() {
        return "Android ${android.os.Build.VERSION.SDK_INT}"
    }
