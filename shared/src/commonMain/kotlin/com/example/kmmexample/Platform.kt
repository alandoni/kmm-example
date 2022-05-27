package com.example.kmmexample

enum class Platform {
    iOS, Android, Web, Windows, Linux, MacOSX, Jvm
}

expect fun getPlatform(): Platform