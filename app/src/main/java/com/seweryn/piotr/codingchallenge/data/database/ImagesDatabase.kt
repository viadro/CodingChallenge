package com.seweryn.piotr.codingchallenge.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.seweryn.piotr.codingchallenge.data.database.model.ImageQuery

@Database(entities = [ImageQuery::class], version = 1)
abstract class ImagesDatabase : RoomDatabase() {

  companion object {
    const val NAME = "images_db"
  }

  abstract val imagesDao: ImagesDao
}