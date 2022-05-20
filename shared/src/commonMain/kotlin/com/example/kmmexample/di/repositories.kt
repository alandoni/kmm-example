package com.example.kmmexample.di

import com.example.kmmexample.data.repository.LaunchesRepository
import com.example.kmmexample.data.services.LaunchesService
import org.koin.dsl.module

val Repositories = module {
    factory { LaunchesService(get()) }
    factory { LaunchesRepository() }
}