package com.example.kmmexample.backend.di

import com.example.kmmexample.backend.repositories.RocketsRepository
import org.koin.dsl.module

val Repositories = module {
    factory { RocketsRepository(get(), get()) }
}