package com.jvg.gpsapp.util.coroutines

import kotlinx.coroutines.CompletableJob
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlin.coroutines.CoroutineContext

class CoroutineProvider : AutoCloseable {
    private val supervisorJob: CompletableJob = SupervisorJob()

    val mainDispatcher: CoroutineDispatcher = Dispatchers.Main
    val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
    val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default

    val mainScope: CoroutineScope = createScope(mainDispatcher)
    val ioScope: CoroutineScope = createScope(ioDispatcher)
    val defaultScope: CoroutineScope = createScope(defaultDispatcher)

    fun createScope(coroutineContext: CoroutineContext): CoroutineScope {
        return CoroutineScope(supervisorJob + coroutineContext)
    }

    override fun close() {
        supervisorJob.complete()
        mainScope.cancel()
        ioScope.cancel()
        defaultScope.cancel()
    }
}
