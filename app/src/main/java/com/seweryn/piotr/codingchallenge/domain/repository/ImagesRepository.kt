package com.seweryn.piotr.codingchallenge.domain.repository

import com.seweryn.piotr.codingchallenge.domain.model.Image

interface ImagesRepository {
  suspend fun fetchImages(query: String): Result<List<Image>>
  suspend fun saveImageQuery(query: String, images: List<Image>): Result<Unit>
  suspend fun getSavedImageQuery(query: String): Result<List<Image>>
  suspend fun getSavedImage(id: Long): Result<Image>
}