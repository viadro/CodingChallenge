package com.seweryn.piotr.codingchallenge.domain.repository

import com.seweryn.piotr.codingchallenge.domain.model.Image
import com.seweryn.piotr.codingchallenge.domain.model.Outcome

interface ImagesRepository {
  suspend fun fetchImages(query: String): Outcome<List<Image>>
  suspend fun saveImageQuery(query: String, images: List<Image>): Outcome<Unit>
  suspend fun getSavedImageQuery(query: String): Outcome<List<Image>>
  suspend fun getSavedImage(id: Long): Outcome<Image>
}