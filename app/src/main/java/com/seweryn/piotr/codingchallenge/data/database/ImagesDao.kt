package com.seweryn.piotr.codingchallenge.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.seweryn.piotr.codingchallenge.data.database.model.ImageEntity

@Dao
interface ImagesDao {
  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertImages(images: List<ImageEntity>)

  @Query("SELECT * FROM ImageEntity WHERE :query = search")
  suspend fun getImages(query: String): List<ImageEntity>

  @Query("SELECT * FROM ImageEntity WHERE :id = id")
  suspend fun getImage(id: Long): ImageEntity

}