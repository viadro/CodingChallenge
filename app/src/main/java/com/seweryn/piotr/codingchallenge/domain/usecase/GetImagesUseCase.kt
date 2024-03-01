package com.seweryn.piotr.codingchallenge.domain.usecase

import com.seweryn.piotr.codingchallenge.domain.model.Image
import com.seweryn.piotr.codingchallenge.domain.repository.ImagesRepository
import com.seweryn.piotr.codingchallenge.network.exception.NetworkConnectionException

interface GetImagesUseCase {
  data class Params(
    val query: String
  )

  suspend operator fun invoke(params: Params): Result<List<Image>>
}

class GetImagesUseCaseImpl(
  private val repository: ImagesRepository
) : GetImagesUseCase {
  override suspend fun invoke(params: GetImagesUseCase.Params): Result<List<Image>> =
    repository.fetchImages(params.query)
      .onSuccess { images ->
        repository.saveImageQuery(
          query = params.query,
          images = images
        )
        Result.success(images)
      }
      .onFailure { throwable ->
        when (throwable) {
          is NetworkConnectionException -> repository.getSavedImageQuery(
            query = params.query
          )
        }
      }

}