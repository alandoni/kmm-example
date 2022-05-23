package com.example.kmmexample.backend.di

import com.example.kmmexample.backend.repositories.RocketsRepository
import com.example.kmmexample.data.database.DatabaseDriverFactory
import org.koin.dsl.module

val Repositories = module {
    single { DatabaseDriverFactory().createDriver() }
    factory { RocketsRepository(get(), get()) }
}