package com.seweryn.piotr.codingchallenge.data.repository

import com.seweryn.piotr.codingchallenge.data.api.ImagesApi
import com.seweryn.piotr.codingchallenge.data.toDomain
import com.seweryn.piotr.codingchallenge.domain.model.Image
import com.seweryn.piotr.codingchallenge.domain.repository.ImagesRepository

class ImagesRepositoryImpl(
  private val api: ImagesApi
) : ImagesRepository {
  override suspend fun fetchImages(query: String): Result<List<Image>> =
    try {
      Result.success(
        api.getImages(query).toDomain()
      )
    } catch (e: Exception) {
      Result.failure(e)
    }
}