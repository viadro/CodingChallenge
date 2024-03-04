package com.seweryn.piotr.codingchallenge.data.database.mapper

import com.seweryn.piotr.codingchallenge.data.database.model.ImageEntity
import com.seweryn.piotr.codingchallenge.domain.model.Image

fun Image.toEntity(query: String) =
  ImageEntity(
    id = id,
    search = query,
    previewURL = previewURL,
    largeImageURL = largeImageURL,
    user = user,
    likes = likes,
    comments = comments,
    downloads = downloads,
    tags = tags,
  )

fun ImageEntity.toDomain() =
  Image(
    id = id,
    previewURL = previewURL,
    largeImageURL = largeImageURL,
    user = user,
    likes = likes,
    comments = comments,
    downloads = downloads,
    tags = tags,
  )