package com.jvg.gpsapp.database.driver

import android.content.Context
import androidx.sqlite.db.SupportSQLiteDatabase
import app.cash.sqldelight.async.coroutines.synchronous
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.jvg.gpstracker.database.GPSDb

interface DriverFactory {
    fun createDriver(): SqlDriver
}

class DefaultDriverFactory(
    private val context: Context,
) : DriverFactory {
    override fun createDriver(): SqlDriver {
        val schema = GPSDb.Schema.synchronous()
        val driver = AndroidSqliteDriver(
            schema = schema,
            context = context,
            name = "gpsapp.db",
            callback = object : AndroidSqliteDriver.Callback(schema) {
                override fun onOpen(db: SupportSQLiteDatabase) {
                    db.setForeignKeyConstraintsEnabled(true)
                }
            }
        )
        return driver
    }
}
