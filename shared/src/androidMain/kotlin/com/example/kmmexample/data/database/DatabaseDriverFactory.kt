package com.example.kmmexample.data.database

import android.content.Context
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

actual class DatabaseDriverFactory: KoinComponent {
    private val context: Context by inject()

    actual fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(AppDatabase.Schema, context, "test.db")
    }
}