package com.seweryn.piotr.codingchallenge.data.repository

import com.seweryn.piotr.codingchallenge.data.api.ImagesApi
import com.seweryn.piotr.codingchallenge.data.database.ImagesDao
import com.seweryn.piotr.codingchallenge.data.database.mapper.toDomain
import com.seweryn.piotr.codingchallenge.data.database.mapper.toEntity
import com.seweryn.piotr.codingchallenge.data.toDomain
import com.seweryn.piotr.codingchallenge.data.toRequest
import com.seweryn.piotr.codingchallenge.domain.model.Image
import com.seweryn.piotr.codingchallenge.domain.model.Outcome
import com.seweryn.piotr.codingchallenge.domain.repository.ImagesRepository

class ImagesRepositoryImpl(
  private val api: ImagesApi,
  private val imagesDao: ImagesDao
) : ImagesRepository {
  override suspend fun fetchImages(query: String): Outcome<List<Image>> =
    try {
      Outcome.Success(
        api.getImages(query.toRequest()).toDomain()
      )
    } catch (e: Exception) {
      Outcome.Failure(e)
    }

  override suspend fun saveImageQuery(query: String, images: List<Image>) =
    try {
      imagesDao.insertImages(
        images.map { it.toEntity(query) }
      )
      Outcome.Success(Unit)
    } catch (e: Exception) {
      Outcome.Failure(e)
    }

  override suspend fun getSavedImageQuery(query: String) =
    try {
      Outcome.Success(
        imagesDao.getImages(query).map {
          it.toDomain()
        }
      )
    } catch (e: Exception) {
      Outcome.Failure(e)
    }

  override suspend fun getSavedImage(id: Long): Outcome<Image> =
    try {
      Outcome.Success(
        imagesDao.getImage(id).toDomain()
      )
    } catch (e: Exception) {
      Outcome.Failure(e)
    }
}