package com.example.kmmexample.data.database

import com.squareup.sqldelight.Transacter
import com.squareup.sqldelight.db.SqlCursor
import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.db.SqlPreparedStatement

actual class DatabaseDriverFactory {
    actual fun createDriver(): SqlDriver {
        return object: SqlDriver {
            override fun close() {
            }

            override fun currentTransaction(): Transacter.Transaction? {
                return null
            }

            override fun execute(
                identifier: Int?,
                sql: String,
                parameters: Int,
                binders: (SqlPreparedStatement.() -> Unit)?
            ) {
            }

            override fun executeQuery(
                identifier: Int?,
                sql: String,
                parameters: Int,
                binders: (SqlPreparedStatement.() -> Unit)?
            ): SqlCursor {
                return object: SqlCursor {
                    override fun close() {
                    }

                    override fun getBytes(index: Int): ByteArray? {
                        return null
                    }

                    override fun getDouble(index: Int): Double? {
                        return null
                    }

                    override fun getLong(index: Int): Long? {
                        return null
                    }

                    override fun getString(index: Int): String? {
                        return null
                    }

                    override fun next(): Boolean {
                        return false
                    }
                }
            }

            override fun newTransaction(): Transacter.Transaction {
                return object: Transacter.Transaction() {
                    override val enclosingTransaction: Transacter.Transaction?
                        get() = null

                    override fun endTransaction(successful: Boolean) {
                    }
                }
            }
        }
    }
}