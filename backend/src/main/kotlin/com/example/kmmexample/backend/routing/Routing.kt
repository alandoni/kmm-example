package com.example.kmmexample.backend.routing

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        rockets()
        get {
            call.respondText("Hello World!", ContentType.Text.Plain)
        }
    }
}