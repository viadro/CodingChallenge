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
      open val searchAction: (String) -> Unit,
      open val onQueryChanged: (String) -> Unit,
      open val query: String,
      val searchEnabled: Boolean = true,
    ) {
      data class Loading(
        override val query: String = "",
      ) : Data(
        query = query,
        searchEnabled = false,
        searchAction = {},
        onQueryChanged = {},
      )

      data class Empty(
        override val query: String = "",
        override val searchAction: (String) -> Unit,
        override val onQueryChanged: (String) -> Unit,
      ) : Data(
        searchAction = searchAction,
        onQueryChanged = onQueryChanged,
        query = query,
      )

      data class Results(
        override val query: String = "",
        val images: List<ImageListItem>,
        override val searchAction: (String) -> Unit,
        override val onQueryChanged: (String) -> Unit,
      ) : Data(
        searchAction = searchAction,
        onQueryChanged = onQueryChanged,
        query = query,
      )
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
    MutableStateFlow<ImagesList.ViewModel.Data>(ImagesList.ViewModel.Data.Loading(INITIAL_QUERY))

  init {
    getImages(INITIAL_QUERY)
  }

  private fun getImages(query: String) = viewModelScope.launch {
    state.emit(ImagesList.ViewModel.Data.Loading(query))
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
      onSearch = ::getImages
    )
  )

  private fun onListItemClicked(item: Image) = viewModelScope.launch {
    navAction.emit(
      ImagesList.Navigation.Action.ImageDetails(item.id)
    )
  }

  private companion object {
    const val INITIAL_QUERY = "fruits"
  }

}