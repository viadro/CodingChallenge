package com.seweryn.piotr.codingchallenge.data

import com.seweryn.piotr.codingchallenge.data.model.ImagesListResponse
import com.seweryn.piotr.codingchallenge.domain.model.Image

fun ImagesListResponse.toDomain() = hits.map { image ->
  Image(
    id = image.id,
    previewURL = image.previewURL,
    largeImageURL = image.largeImageURL,
    user = image.user,
    likes = image.likes,
    comments = image.comments,
    downloads = image.downloads,
    tags = image.tags.split(", ")
  )
}