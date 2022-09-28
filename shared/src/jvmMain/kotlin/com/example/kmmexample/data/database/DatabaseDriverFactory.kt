package com.example.kmmexample.data.database

import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.sqlite.driver.JdbcSqliteDriver
import java.io.File

actual class DatabaseDriverFactory {
    actual fun createDriver(): SqlDriver {
        // For Server Database
        //val ds = HikariDataSource()
        //ds.username = "root"
        //ds.password = "root"

        // For MySQL
        //ds.driverClassName = "com.mysql.jdbc.Driver"
        //ds.jdbcUrl = "jdbc:mysql://localhost:3306/movies_db"

        // For Postgres
        //ds.driverClassName = "org.postgresql.ds.PGSimpleDataSource"
        //ds.jdbcUrl =  "jdbc:postgresql://localhost:5432/test"

        //return ds.asJdbcDriver()

        // For SQLITE

        val file = File(
            System.getProperty("user.dir") + File.separator + ".." + File.separator + "db"
        )
        if (!file.exists()) {
            file.mkdir()
        }
        val driver = JdbcSqliteDriver(
            "jdbc:sqlite:${file.absolutePath}${File.separator}database.db"
        )
        if (driver.getVersion() == 0) {
            AppDatabase.Schema.create(driver)
            driver.setVersion(1)
        } else {
            AppDatabase.invoke(
                driver,
            )
        }
        return driver
    }
}

fun SqlDriver.getVersion(): Int {
    val sqlCursor = executeQuery(
        null,
        "PRAGMA user_version;",
        0,
        null
    )
    return sqlCursor.use { sqlCursor.getLong(0)?.toInt() ?: 0 }
}

fun SqlDriver.setVersion(version: Int) {
    execute(
        null,
        String.format("PRAGMA user_version = %d;", version),
        0,
        null
    )
}