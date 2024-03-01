package com.seweryn.piotr.codingchallenge.presentation.list.mapper

import com.seweryn.piotr.codingchallenge.common.Mapper
import com.seweryn.piotr.codingchallenge.domain.model.Image
import com.seweryn.piotr.codingchallenge.presentation.list.ImagesList
import com.seweryn.piotr.codingchallenge.presentation.list.model.ImageListItem


class ImagesListScreenMapper : Mapper<ImagesListScreenMapper.Params, ImagesList.ViewModel.Data> {
  data class Params(
    val images: List<Image>,
    val onListItemClicked: (Image) -> Unit,
    val onSearchButtonClicked: (String) -> Unit
  )

  override fun invoke(params: Params) =
    if (params.images.isEmpty()) {
      ImagesList.ViewModel.Data.Empty(
        searchButtonAction = params.onSearchButtonClicked,
      )
    } else {
      ImagesList.ViewModel.Data.Results(
        images = params.images.map { image ->
          ImageListItem(
            userName = image.user,
            thumbnailUrl = image.previewURL,
            tags = image.tags,
          )
        },
        searchButtonAction = params.onSearchButtonClicked,
      )
    }
}