package com.jvg.gpsapp.util

import com.jvg.gpsapp.resources.R
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimePeriod
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format.DateTimeFormat
import kotlinx.datetime.format.char
import kotlinx.datetime.minus
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime

object Dates {
    val currentTime: LocalDateTime = Clock.System.now().toLocalDateTime(TimeZone.UTC)
    val readableFormat: DateTimeFormat<LocalDateTime> = LocalDateTime.Format {
        date(LocalDate.Formats.ISO)
        char(' ')
        time(LocalTime.Formats.ISO)
    }
    val yesterday: LocalDateTime = currentTime
        .toInstant(TimeZone.Companion.UTC)
        .minus(DateTimePeriod(days = 1), TimeZone.Companion.UTC)
        .toLocalDateTime(TimeZone.Companion.UTC)

    fun formatMonthName(month: Int): Int {
        return when (month) {
            1 -> R.string.month_jan
            2 -> R.string.month_feb
            3 -> R.string.month_mar
            4 -> R.string.month_apr
            5 -> R.string.month_may
            6 -> R.string.month_jun
            7 -> R.string.month_jul
            8 -> R.string.month_ago
            9 -> R.string.month_sep
            10 -> R.string.month_oct
            11 -> R.string.month_nov
            12 -> R.string.month_dec
            else -> R.string.unspecified
        }
    }

    fun LocalDateTime.toReadableDate(): String {
        return "${this.date.dayOfMonth} ${formatMonthName(this.dayOfMonth)}, ${this.year}"
    }

    fun LocalDateTime.formatDate(): String {
        return "${this.date} ${this.time}"
    }
}
