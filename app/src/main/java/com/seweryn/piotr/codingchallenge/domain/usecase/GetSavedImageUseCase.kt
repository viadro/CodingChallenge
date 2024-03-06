package com.seweryn.piotr.codingchallenge.domain.usecase

import com.seweryn.piotr.codingchallenge.domain.model.Image
import com.seweryn.piotr.codingchallenge.domain.model.Outcome
import com.seweryn.piotr.codingchallenge.domain.repository.ImagesRepository

interface GetSavedImageUseCase {
  data class Params(
    val id: Long,
  )

  suspend operator fun invoke(params: Params): Outcome<Image>
}

class GetSavedImageUseCaseImpl(
  private val repository: ImagesRepository,
) : GetSavedImageUseCase {

  override suspend fun invoke(params: GetSavedImageUseCase.Params): Outcome<Image> =
    repository.getSavedImage(params.id)

}