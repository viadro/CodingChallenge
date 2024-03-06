package com.seweryn.piotr.codingchallenge.presentation.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seweryn.piotr.codingchallenge.domain.usecase.GetSavedImageUseCase
import com.seweryn.piotr.codingchallenge.presentation.ScreenViewModel
import com.seweryn.piotr.codingchallenge.presentation.details.mapper.ImageDetailsScreenMapper
import com.seweryn.piotr.codingchallenge.presentation.error.mapper.ErrorMapper
import com.seweryn.piotr.codingchallenge.presentation.error.model.ErrorData
import com.seweryn.piotr.codingchallenge.presentation.navigation.Destination
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

interface ImageDetails {
  interface ViewModel : ScreenViewModel<ViewModel.Data> {
    sealed interface Data {
      data object Empty : Data
      data class Error(
        val data: ErrorData
      ) : Data

      data class Image(
        val imageUrl: String,
        val userName: String,
        val tags: List<String>,
        val likes: String,
        val downloads: String,
        val comments: String,
      ) : Data
    }
  }
}

@HiltViewModel
class ImageDetailsViewModel @Inject constructor(
  savedStateHandle: SavedStateHandle,
  private val getSavedImageUseCase: GetSavedImageUseCase,
  private val screenMapper: ImageDetailsScreenMapper,
  private val errorMapper: ErrorMapper,
) :
  ViewModel(),
  ImageDetails.ViewModel {

  private val id: Long = checkNotNull(savedStateHandle[Destination.DETAILS_ID])

  init {
    getImage()
  }

  override val state =
    MutableStateFlow<ImageDetails.ViewModel.Data>(ImageDetails.ViewModel.Data.Empty)

  private fun getImage() = viewModelScope.launch {
    getSavedImageUseCase(
      GetSavedImageUseCase.Params(
        id = id,
      )
    ).suspendExecute(
      onSuccess = { image ->
        state.emit(
          screenMapper(
            ImageDetailsScreenMapper.Params(
              image = image
            )
          )
        )
      },
      onFailure = { error, _ ->
        state.emit(
          ImageDetails.ViewModel.Data.Error(
            data = errorMapper(error)
          )
        )
      }
    )
  }

}