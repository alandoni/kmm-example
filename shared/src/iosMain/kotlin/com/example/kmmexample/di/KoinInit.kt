package com.example.kmmexample.di

import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

actual class KoinInit {
    actual fun start(platformSpecific: KoinAppDeclaration?) {
        startKoin {
            modules(
                Network,
                Database,
                Repositories,
                DatabaseDriver
            )
        }
    }
}