package com.seweryn.piotr.codingchallenge.data.repository

import com.seweryn.piotr.codingchallenge.data.api.ImagesApi
import com.seweryn.piotr.codingchallenge.data.database.ImagesDao
import com.seweryn.piotr.codingchallenge.data.database.mapper.toDomain
import com.seweryn.piotr.codingchallenge.data.database.mapper.toEntity
import com.seweryn.piotr.codingchallenge.data.toDomain
import com.seweryn.piotr.codingchallenge.data.toRequest
import com.seweryn.piotr.codingchallenge.domain.model.Image
import com.seweryn.piotr.codingchallenge.domain.repository.ImagesRepository

class ImagesRepositoryImpl(
  private val api: ImagesApi,
  private val imagesDao: ImagesDao
) : ImagesRepository {
  override suspend fun fetchImages(query: String): Result<List<Image>> =
    try {
      Result.success(
        api.getImages(query.toRequest()).toDomain()
      )
    } catch (e: Exception) {
      Result.failure(e)
    }

  override suspend fun saveImageQuery(query: String, images: List<Image>) =
    try {
      imagesDao.insertImages(
        images.map { it.toEntity(query) }
      )
      Result.success(Unit)
    } catch (e: Exception) {
      Result.failure(e)
    }

  override suspend fun getSavedImageQuery(query: String) =
    try {
      Result.success(
        imagesDao.getImages(query).map {
          it.toDomain()
        }
      )
    } catch (e: Exception) {
      Result.failure(e)
    }
}