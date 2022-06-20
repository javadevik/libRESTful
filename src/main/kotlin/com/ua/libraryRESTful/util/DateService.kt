package com.ua.libraryRESTful.util

import org.springframework.stereotype.Service
import java.sql.Timestamp
import java.text.SimpleDateFormat

@Service
class DateService(
    private val dateFormat: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
) {
    fun parseToTimestamp(date: String): Timestamp {
        return Timestamp(dateFormat.parse(date).time)
    }
}