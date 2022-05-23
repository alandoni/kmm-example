package com.example.kmmexample.data.database

import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.sqlite.driver.asJdbcDriver
import com.zaxxer.hikari.HikariDataSource

actual class DatabaseDriverFactory {
    actual fun createDriver(): SqlDriver {
        val ds = HikariDataSource()
        ds.jdbcUrl = "jdbc:mysql://localhost:3306/movies_db"
        ds.driverClassName = "com.mysql.jdbc.Driver"
        ds.username = "root"
        ds.password = "mysqlroot"

        //ds.driverClassName = "org.postgresql.ds.PGSimpleDataSource"
        //ds.jdbcUrl =  "jdbc:postgresql://localhost:5432/test"
        return ds.asJdbcDriver()
    }
}