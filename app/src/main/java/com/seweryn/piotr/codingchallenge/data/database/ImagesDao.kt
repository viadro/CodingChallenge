package com.seweryn.piotr.codingchallenge.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.seweryn.piotr.codingchallenge.data.database.model.ImageQuery

@Dao
interface ImagesDao {
  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertImageQuery(query: ImageQuery)

  @Query("SELECT * FROM ImageQuery WHERE :query = search")
  suspend fun getImageQuery(query: String): ImageQuery

}