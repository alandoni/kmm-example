package com.example.kmmexample.di

import com.example.kmmexample.data.database.DatabaseDriverFactory
import org.koin.dsl.module

val DatabaseDriver = module {
    single { DatabaseDriverFactory().createDriver() }
}