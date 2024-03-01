package com.seweryn.piotr.codingchallenge.domain.repository

import com.seweryn.piotr.codingchallenge.domain.model.Image

interface ImagesRepository {
  suspend fun fetchImages(query: String): Result<List<Image>>
}