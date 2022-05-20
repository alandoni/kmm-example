package com.example.kmmexample.di

import org.koin.dsl.KoinAppDeclaration

expect class KoinInit {
    fun start(platformSpecific: KoinAppDeclaration? = null)
}