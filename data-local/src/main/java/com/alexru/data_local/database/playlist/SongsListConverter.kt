package com.alexru.data_local.database.playlist

import androidx.room.TypeConverter

/**
 * Converter between List<Int> and String
 */
class SongsListConverter {
    /**
     * TypeConverter from List<Int> to String
     */
    @TypeConverter
    fun fromIntList(intList: List<Int>): String {
        return intList.joinToString(separator = ",")
    }

    /**
     * TypeConverter from String to List<Int>
     */
    @TypeConverter
    fun toIntList(intString: String): List<Int> {
        return if (intString.isNotEmpty()) {
            intString.split(",").map { it.toInt() }
        } else {
            emptyList()
        }
    }
}