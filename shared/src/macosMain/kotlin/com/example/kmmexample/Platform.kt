package com.example.kmmexample

actual fun getPlatform() = Platform.MacOSX

val Platform.getSystemName: String
    get() {
        return "Mac OSX"
    }
