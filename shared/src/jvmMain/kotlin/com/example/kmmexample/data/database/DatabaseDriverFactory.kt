package com.example.kmmexample.data.database

import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.sqlite.driver.JdbcSqliteDriver
import com.squareup.sqldelight.sqlite.driver.asJdbcDriver
import com.zaxxer.hikari.HikariDataSource

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
        val driver = JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY)
        AppDatabase.Schema.create(driver)
        return driver
    }
}