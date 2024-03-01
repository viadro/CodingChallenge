package com.seweryn.piotr.codingchallenge.data.repository

import com.seweryn.piotr.codingchallenge.data.api.ImagesApi
import com.seweryn.piotr.codingchallenge.data.database.ImagesDatabase
import com.seweryn.piotr.codingchallenge.data.database.model.ImageQuery
import com.seweryn.piotr.codingchallenge.data.toDomain
import com.seweryn.piotr.codingchallenge.domain.model.Image
import com.seweryn.piotr.codingchallenge.domain.repository.ImagesRepository

class ImagesRepositoryImpl(
  private val api: ImagesApi,
  private val database: ImagesDatabase
) : ImagesRepository {
  override suspend fun fetchImages(query: String): Result<List<Image>> =
    try {
      Result.success(
        api.getImages(query).toDomain()
      )
    } catch (e: Exception) {
      Result.failure(e)
    }

  override suspend fun saveImageQuery(query: String, images: List<Image>) =
    try {
      database.imagesDao.insertImageQuery(
        ImageQuery(
          search = query,
          images = images
        )
      )
      Result.success(Unit)
    } catch (e: Exception) {
      Result.failure(e)
    }

  override suspend fun getSavedImageQuery(query: String) =
    try {
      Result.success(
        database.imagesDao.getImageQuery(query).images
      )
    } catch (e: Exception) {
      Result.failure(e)
    }
}