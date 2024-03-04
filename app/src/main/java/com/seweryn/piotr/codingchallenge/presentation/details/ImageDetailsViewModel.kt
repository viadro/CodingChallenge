package com.seweryn.piotr.codingchallenge.presentation.details

import androidx.lifecycle.ViewModel
import com.seweryn.piotr.codingchallenge.presentation.ScreenViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow

interface ImageDetails {
  interface ViewModel : ScreenViewModel<ViewModel.Data> {
    data class Data(
      val imageUrl: String,
      val userName: String,
      val tags: List<String>,
      val likes: String,
      val downloads: String,
      val comments: String,
    )
  }
}

@HiltViewModel
class ImageDetailsViewModel :
  ViewModel(),
  ImageDetails.ViewModel {
  override val state: StateFlow<ImageDetails.ViewModel.Data>
    get() = TODO("Not yet implemented")

}