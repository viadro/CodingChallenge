package com.seweryn.piotr.codingchallenge.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seweryn.piotr.codingchallenge.common.ChannelFlow
import com.seweryn.piotr.codingchallenge.domain.model.Image
import com.seweryn.piotr.codingchallenge.domain.usecase.GetImagesUseCase
import com.seweryn.piotr.codingchallenge.presentation.NavigationViewModel
import com.seweryn.piotr.codingchallenge.presentation.ScreenViewModel
import com.seweryn.piotr.codingchallenge.presentation.list.mapper.ImagesListScreenMapper
import com.seweryn.piotr.codingchallenge.presentation.list.model.ImageListItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

interface ImagesList {
  interface ViewModel : ScreenViewModel<ViewModel.Data> {
    sealed class Data(
      open val searchButtonAction: (String) -> Unit,
      val searchButtonEnabled: Boolean = true,
    ) {
      data object Loading : Data(
        searchButtonEnabled = false,
        searchButtonAction = {},
      )

      data class Empty(
        override val searchButtonAction: (String) -> Unit,
      ) : Data(searchButtonAction)

      data class Results(
        val images: List<ImageListItem>,
        override val searchButtonAction: (String) -> Unit,
      ) : Data(searchButtonAction)
    }
  }

  interface Navigation : NavigationViewModel<Navigation.Action> {
    sealed interface Action {
      data class ImageDetails(val id: Long) : Action
    }
  }
}

@HiltViewModel
class ImagesListViewModel @Inject constructor(
  private val getImagesUseCase: GetImagesUseCase,
  private val screenMapper: ImagesListScreenMapper,
) : ViewModel(),
  ImagesList.ViewModel,
  ImagesList.Navigation {

  override val navAction = ChannelFlow<ImagesList.Navigation.Action>()
  override val state =
    MutableStateFlow<ImagesList.ViewModel.Data>(ImagesList.ViewModel.Data.Loading)

  init {
    getImages(INITIAL_QUERY)
  }

  private fun getImages(query: String) = viewModelScope.launch {
    state.emit(ImagesList.ViewModel.Data.Loading)
    getImagesUseCase(
      GetImagesUseCase.Params(query = query)
    ).onSuccess { images ->
      state.emit(images.map())
    }
  }

  private fun List<Image>.map() = screenMapper(
    ImagesListScreenMapper.Params(
      images = this,
      onListItemClicked = ::onListItemClicked,
      onSearchButtonClicked = ::getImages
    )
  )

  private fun onListItemClicked(item: Image) = viewModelScope.launch {
    navAction.emit(
      ImagesList.Navigation.Action.ImageDetails(item.id)
    )
  }

  private companion object {
    const val INITIAL_QUERY = "a"
  }

}