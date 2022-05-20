package com.example.kmmexample.data.services

import com.example.kmmexample.data.models.RocketLaunch
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*

class LaunchesService(
    private val httpClient: HttpClient
) {
    companion object {
        private const val LAUNCHES_ENDPOINT = "https://api.spacexdata.com/v3/launches"
    }

    suspend fun getLaunches(): List<RocketLaunch> {
        return httpClient.get(LAUNCHES_ENDPOINT).body()
    }
}