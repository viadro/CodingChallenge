package com.seweryn.piotr.codingchallenge.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.seweryn.piotr.codingchallenge.data.database.converter.StringListTypeConverter
import com.seweryn.piotr.codingchallenge.data.database.model.ImageEntity

@Database(entities = [ImageEntity::class], version = 1)
@TypeConverters(
  StringListTypeConverter::class,
)
abstract class ImagesDatabase : RoomDatabase() {

  companion object {
    const val NAME = "images_db"
  }

  abstract val imagesDao: ImagesDao
}