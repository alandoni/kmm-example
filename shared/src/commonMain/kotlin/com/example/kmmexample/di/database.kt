package com.example.kmmexample.di

import com.example.kmmexample.data.database.AppDatabase
import com.example.kmmexample.data.database.DatabaseDriverFactory
import org.koin.dsl.module

val Database = module {
    factory { DatabaseDriverFactory() }
    single { get<DatabaseDriverFactory>().createDriver() }
    factory { AppDatabase(get()) }
    factory { get<AppDatabase>().launchesQueries }
    factory { get<AppDatabase>().rocketQueries }
}