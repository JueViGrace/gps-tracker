package com.jvg.gpsapp.util

object Logs {
    enum class LogType {
        DEBUG,
        INFO,
        WARN,
        ERROR
    }

    fun warn(tag: String, msg: String, tr: Throwable? = null) {
        log(LogType.WARN, tag, msg, tr)
    }

    fun error(tag: String, msg: String?, tr: Throwable? = null) {
        log(LogType.ERROR, tag, msg ?: "Unknown error", tr)
    }

    fun info(tag: String, msg: String, tr: Throwable? = null) {
        log(LogType.INFO, tag, msg, tr)
    }

    fun debug(tag: String, msg: String, tr: Throwable? = null) {
        log(LogType.DEBUG, tag, msg, tr)
    }

    private fun log(type: LogType, tag: String, msg: String, tr: Throwable? = null) {
        println("[${type.name}] $tag: $msg. Exception: ${tr?.message}")
    }
}
