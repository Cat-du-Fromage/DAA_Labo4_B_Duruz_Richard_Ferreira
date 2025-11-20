package ch.heigvd.iict.daa.lab4_b.repository

import androidx.room.*
import java.util.Calendar
import java.util.Date

class CalendarConverter {

    /**
     * Converts a long to a calendar.
     */
    @TypeConverter
    fun toCalendar(dateLong: Long): Calendar = Calendar.getInstance().apply {
        time = Date(dateLong)
    }

    /**
     * Converts a calendar to a long.
     */
    @TypeConverter
    fun fromCalendar(calendar: Calendar): Long = calendar.time.time
}