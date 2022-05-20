package com.example.kmmexample.di

import com.example.kmmexample.data.database.AppDatabase
import org.koin.dsl.module

val Database = module {
    factory { AppDatabase(get()) }
    factory { get<AppDatabase>().launchesQueries }
    factory { get<AppDatabase>().rocketQueries }
}