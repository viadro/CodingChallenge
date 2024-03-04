package com.seweryn.piotr.codingchallenge.data.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ImageEntity(
  @PrimaryKey
  val id: Long,
  val search: String,
  val previewURL: String,
  val largeImageURL: String,
  val user: String,
  val likes: Int,
  val comments: Int,
  val downloads: Long,
  val tags: List<String>,
)