package com.example.kmmexample.di

import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

class KoinInit {
    companion object {
        fun start(platformSpecific: KoinAppDeclaration?) {
            startKoin {
                platformSpecific?.let { it() }
                modules(
                    Network,
                    Database,
                    Repositories,
                )
            }
        }
    }
}