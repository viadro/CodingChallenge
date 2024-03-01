package com.seweryn.piotr.codingchallenge.data.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.seweryn.piotr.codingchallenge.domain.model.Image

@Entity
data class ImageQuery(
  @PrimaryKey
  val search: String,
  val images: List<Image>
)