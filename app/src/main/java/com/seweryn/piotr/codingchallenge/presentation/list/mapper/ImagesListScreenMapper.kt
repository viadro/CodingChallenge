package com.seweryn.piotr.codingchallenge.presentation.list.mapper

import com.seweryn.piotr.codingchallenge.common.Mapper
import com.seweryn.piotr.codingchallenge.domain.model.Image
import com.seweryn.piotr.codingchallenge.presentation.error.mapper.ErrorMapper
import com.seweryn.piotr.codingchallenge.presentation.list.ImagesList
import com.seweryn.piotr.codingchallenge.presentation.list.model.ImageListItem
import javax.inject.Inject


class ImagesListScreenMapper @Inject constructor(
  private val errorMapper: ErrorMapper,
) :
  Mapper<ImagesListScreenMapper.Params, ImagesList.ViewModel.Data> {
  data class Params(
    val query: String,
    val images: List<Image>,
    val onListItemClicked: (Image) -> Unit,
    val onSearch: () -> Unit,
    val onQueryChanged: (String) -> Unit,
    val error: Throwable?,
    val onConfirmDialog: (Image) -> Unit,
    val onDismissDialog: () -> Unit,
  )

  override fun invoke(params: Params) =
    if (params.images.isEmpty()) {
      ImagesList.ViewModel.Data.Empty(
        query = params.query,
        searchAction = params.onSearch,
        onQueryChanged = params.onQueryChanged,
      )
    } else {
      ImagesList.ViewModel.Data.Results(
        query = params.query,
        images = params.images.map { image ->
          ImageListItem(
            userName = image.user,
            thumbnailUrl = image.previewURL,
            tags = image.tags,
            onClick = {
              params.onListItemClicked(image)
            },
          )
        },
        searchAction = params.onSearch,
        onQueryChanged = params.onQueryChanged,
        onConfirmDialog = params.onConfirmDialog,
        onDismissDialog = params.onDismissDialog,
        error = params.error?.let {
          errorMapper(it)
        }
      )
    }
}