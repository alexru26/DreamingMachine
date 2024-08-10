package com.alexru.domain.model

import java.time.LocalDate
import kotlin.time.Duration

/**
 * Album object
 */
data class Album(
    val id: Int,
    val name: String,
    val artist: String,
    val type: String,
    val length: Duration,
    val release: LocalDate,
    val cover: String
)
