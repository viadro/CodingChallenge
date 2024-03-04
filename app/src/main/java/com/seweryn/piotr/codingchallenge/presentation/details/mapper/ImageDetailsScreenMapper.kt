package com.seweryn.piotr.codingchallenge.presentation.details.mapper

import com.seweryn.piotr.codingchallenge.common.Mapper
import com.seweryn.piotr.codingchallenge.domain.model.Image
import com.seweryn.piotr.codingchallenge.presentation.details.ImageDetails
import javax.inject.Inject

class ImageDetailsScreenMapper @Inject constructor() :
  Mapper<ImageDetailsScreenMapper.Params, ImageDetails.ViewModel.Data> {

  data class Params(
    val image: Image,
  )

  override fun invoke(params: Params): ImageDetails.ViewModel.Data =
    with(params.image) {
      ImageDetails.ViewModel.Data.Image(
        imageUrl = largeImageURL,
        userName = user,
        comments = comments.toString(),
        likes = likes.toString(),
        downloads = downloads.toString(),
        tags = tags,
      )
    }
}