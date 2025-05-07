package com.jvg.gpsapp.database.helpers

import app.cash.sqldelight.Query
import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.coroutines.mapToOneOrNull
import com.jvg.gpsapp.database.driver.DriverFactory
import com.jvg.gpsapp.util.Logs
import com.jvg.gpsapp.util.coroutines.CoroutineProvider
import com.jvg.gpstracker.database.GPSDb
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

interface DbHelper {
    val tag: String
        get() = this::class.simpleName ?: "DbHelper"

    val driver: DriverFactory
    val db: GPSDb
        get() = GPSDb(driver.createDriver())

    val coroutineProvider: CoroutineProvider
    val coroutineContext: CoroutineContext
        get() = coroutineProvider.ioDispatcher
    val scope: CoroutineScope
        get() = coroutineProvider.createScope(coroutineContext)
    val mutex: Mutex
        get() = Mutex()

    fun <T : Any> executeOne(query: Query<T>): T? {
        return query.executeAsOneOrNull()
    }

    fun <T : Any> executeOneAsFlow(query: Query<T>): Flow<T?> {
        return query.asFlow().mapToOneOrNull(coroutineContext)
    }

    fun <T : Any> executeList(query: Query<T>): List<T> {
        return query.executeAsList()
    }

    fun <T : Any> executeListAsFlow(query: Query<T>): Flow<List<T>> {
        return query.asFlow().mapToList(coroutineContext)
    }
}

suspend inline fun<reified T : Any> DbHelper.withDatabase(
    crossinline block: suspend DbHelper.(GPSDb) -> T?
): T? {
    return withContext(coroutineContext) {
        try {
            mutex.withLock {
                block(db)
            }
        } catch (e: Exception) {
            Logs.error(tag = tag, msg = "With database error: ", tr = e)
            null
        }
    }
}
