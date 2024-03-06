package com.seweryn.piotr.codingchallenge.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seweryn.piotr.codingchallenge.common.ChannelFlow
import com.seweryn.piotr.codingchallenge.domain.model.Image
import com.seweryn.piotr.codingchallenge.domain.usecase.GetImagesUseCase
import com.seweryn.piotr.codingchallenge.presentation.NavigationViewModel
import com.seweryn.piotr.codingchallenge.presentation.ScreenViewModel
import com.seweryn.piotr.codingchallenge.presentation.error.mapper.ErrorMapper
import com.seweryn.piotr.codingchallenge.presentation.error.model.ErrorData
import com.seweryn.piotr.codingchallenge.presentation.list.mapper.ImagesListScreenMapper
import com.seweryn.piotr.codingchallenge.presentation.list.model.ImageListItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

interface ImagesList {
  interface ViewModel : ScreenViewModel<ViewModel.Data> {
    sealed class Data(
      open val searchAction: () -> Unit,
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
        override val searchAction: () -> Unit,
        override val onQueryChanged: (String) -> Unit,
      ) : Data(
        searchAction = searchAction,
        onQueryChanged = onQueryChanged,
        query = query,
      )

      data class Error(
        val data: ErrorData,
        override val query: String = "",
        override val searchAction: () -> Unit,
        override val onQueryChanged: (String) -> Unit,
      ) : Data(
        searchAction = searchAction,
        onQueryChanged = onQueryChanged,
        query = query,
      )

      data class Results(
        override val query: String = "",
        val images: List<ImageListItem>,
        val error: ErrorData?,
        override val searchAction: () -> Unit,
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
  private val errorMapper: ErrorMapper,
) : ViewModel(),
  ImagesList.ViewModel,
  ImagesList.Navigation {

  override val navAction = ChannelFlow<ImagesList.Navigation.Action>()
  override val state =
    MutableStateFlow<ImagesList.ViewModel.Data>(ImagesList.ViewModel.Data.Loading(INITIAL_QUERY))

  init {
    getImages()
  }

  private fun getImages() = viewModelScope.launch {
    val query = state.value.query
    state.emit(ImagesList.ViewModel.Data.Loading(query))
    getImagesUseCase(
      GetImagesUseCase.Params(query = query)
    ).suspendExecute(
      onSuccess = { images ->
        state.emit(images.map())
      },
      onFailure = { error, value ->
        state.emit(
          value?.map(error) ?: ImagesList.ViewModel.Data.Error(
            query = state.value.query,
            searchAction = ::getImages,
            onQueryChanged = state.value.onQueryChanged,
            data = errorMapper(error)
          )
        )
      }
    )
  }

  private fun List<Image>.map(error: Throwable? = null) = screenMapper(
    ImagesListScreenMapper.Params(
      query = state.value.query,
      images = this,
      onListItemClicked = ::onListItemClicked,
      onSearch = ::getImages,
      onQueryChanged = ::onQueryChanged,
      error = error,
    )
  )

  private fun onListItemClicked(item: Image) = viewModelScope.launch {
    navAction.emit(
      ImagesList.Navigation.Action.ImageDetails(item.id)
    )
  }

  private fun onQueryChanged(query: String) = viewModelScope.launch {
    state.emit(
      when (val data = state.value) {
        is ImagesList.ViewModel.Data.Loading -> data.copy(query = query)
        is ImagesList.ViewModel.Data.Empty -> data.copy(query = query)
        is ImagesList.ViewModel.Data.Results -> data.copy(query = query)
        is ImagesList.ViewModel.Data.Error -> data.copy(query = query)
      }
    )
  }

  private companion object {
    const val INITIAL_QUERY = "fruits"
  }

}