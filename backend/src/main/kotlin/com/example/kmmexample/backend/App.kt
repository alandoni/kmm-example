package com.example.kmmexample.backend

import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import com.example.kmmexample.backend.routing.*
import com.example.kmmexample.di.Database
import com.example.kmmexample.backend.di.Repositories
import io.ktor.serialization.gson.*
import io.ktor.server.application.*
import org.koin.ktor.plugin.Koin

fun main(args: Array<String>) {
    embeddedServer(Netty, port = 8080, host = "127.0.0.1") {
        install(ContentNegotiation) {
            gson {
                setPrettyPrinting()
                setLenient()
            }
        }
        install(Koin) {
            //modules(HttpClient)
            modules(Database)
            modules(Repositories)
        }
        configureRouting()
    }.start(wait = true)
}
