package com.example.kmmexample.di

import com.example.kmmexample.Platform
import com.example.kmmexample.getPlatform
import com.example.kmmexample.data.persistence.InMemoryLaunchesStory
import com.example.kmmexample.data.persistence.LaunchesPersistence
import com.example.kmmexample.data.repository.LaunchesRepository
import com.example.kmmexample.data.services.LaunchesService
import org.koin.dsl.module

val Repositories = module {
    factory { LaunchesService(get()) }
    factory { LaunchesRepository(get(), get()) }
    factory {
        if (getPlatform() == Platform.Web)
            InMemoryLaunchesStory()
        else
            LaunchesPersistence(get(), get())
    }
}