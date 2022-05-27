package com.example.kmmexample.data.repository

import com.example.kmmexample.data.models.RocketLaunch
import com.example.kmmexample.data.persistence.LaunchesStore
import com.example.kmmexample.data.services.LaunchesService
import org.koin.core.component.KoinComponent

class LaunchesRepository(
    private val service: LaunchesService,
    private val store: LaunchesStore
): KoinComponent {

    @Throws(Exception::class)
    suspend fun getLaunches() =
        try {
            service.getLaunches().also {
                store.saveAllLaunches(it)
            }
            store.getAllLaunches()
        } catch (e: Exception) {
            e.printStackTrace()
            store.getAllLaunches()
        }
}