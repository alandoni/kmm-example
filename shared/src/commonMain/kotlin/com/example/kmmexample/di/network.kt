package com.example.kmmexample.di

import io.ktor.client.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import org.koin.dsl.module

val Network = module {
    factory {
        HttpClient {
            expectSuccess = true
            install(ContentNegotiation) {
                json(Json {
                    isLenient = true
                    ignoreUnknownKeys = true
                })
            }
        }
    }
}