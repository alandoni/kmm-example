package com.example.kmmexample.backend.routing

import com.example.kmmexample.backend.repositories.RocketsRepository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.rockets() {
    val repository: RocketsRepository by inject()

    route("/rockets") {
        get {
            try {
                val response = repository.getLaunches()
                call.respond(HttpStatusCode.OK, response)
            } catch (e: Throwable) {
                e.printStackTrace()
                call.respond(HttpStatusCode.BadRequest, e.stackTrace.toString())
            }
        }
    }
}