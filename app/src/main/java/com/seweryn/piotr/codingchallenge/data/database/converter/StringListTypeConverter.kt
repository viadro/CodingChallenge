package com.seweryn.piotr.codingchallenge.data.database.converter

import androidx.room.TypeConverter

class StringListTypeConverter {

  private companion object {
    const val SEPARATOR = "+"
  }

  @TypeConverter
  fun fromList(list: List<String>): String =
    list.joinToString(SEPARATOR)

  @TypeConverter
  fun toList(joinedList: String): List<String> =
    joinedList.split(SEPARATOR)
}