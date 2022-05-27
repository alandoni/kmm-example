package com.example.kmmexample.data.persistence

import com.example.kmmexample.data.models.RocketLaunch

class InMemoryLaunchesStory: LaunchesStore {
    private val list = mutableListOf<RocketLaunch>()

    override suspend fun saveAllLaunches(launches: List<RocketLaunch>) {
        list.clear()
        list.addAll(launches)
    }

    override suspend fun getAllLaunches(): List<RocketLaunch> = list
}