package com.seweryn.piotr.codingchallenge.domain.usecase

import com.seweryn.piotr.codingchallenge.domain.model.Image
import com.seweryn.piotr.codingchallenge.domain.model.Outcome
import com.seweryn.piotr.codingchallenge.domain.repository.ImagesRepository
import com.seweryn.piotr.codingchallenge.network.exception.NetworkConnectionException

interface GetImagesUseCase {
  data class Params(
    val query: String
  )

  suspend operator fun invoke(params: Params): Outcome<List<Image>>
}

class GetImagesUseCaseImpl(
  private val repository: ImagesRepository
) : GetImagesUseCase {
  override suspend fun invoke(params: GetImagesUseCase.Params): Outcome<List<Image>> =
    repository.fetchImages(params.query).suspendExecute(
      onSuccess = { images ->
        repository.saveImageQuery(
          query = params.query,
          images = images
        )
        Outcome.Success(images)
      },
      onFailure = { throwable, value ->
        when (throwable) {
          is NetworkConnectionException -> repository.getSavedImageQuery(
            query = params.query
          ).suspendExecute(
            onSuccess = { images ->
              Outcome.Failure(
                error = throwable,
                value = images,
              )
            },
            onFailure = { _, _ -> Outcome.Failure(throwable, value) }
          )

          else -> Outcome.Failure(throwable, value)
        }
      }
    )

}