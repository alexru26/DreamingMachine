package com.alexru.data_repository.util

import java.time.LocalDate
import java.time.format.DateTimeFormatter

/**
 * Converter from String to LocalDate
 */
fun String.toLocalDate(): LocalDate {
    val formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy")
    return LocalDate.parse(toString(), formatter)
}

