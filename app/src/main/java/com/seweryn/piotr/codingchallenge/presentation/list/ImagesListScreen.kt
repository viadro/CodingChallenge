package com.seweryn.piotr.codingchallenge.presentation.list

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.seweryn.piotr.codingchallenge.R
import com.seweryn.piotr.codingchallenge.presentation.list.model.ImageListItem

@Composable
fun ImagesListScreen(
  viewModel: ImagesList.ViewModel,
) {
  val data by viewModel.state.collectAsStateWithLifecycle()
  Column(
    modifier = Modifier.padding(16.dp),
  ) {
    Row(
      modifier = Modifier.fillMaxWidth(),
    ) {
      TextField(
        modifier = Modifier.weight(1f),
        value = data.query,
        enabled = data.searchEnabled,
        onValueChange = data.onQueryChanged,
      )
      Image(
        painter = painterResource(id = R.drawable.ic_search),
        contentDescription = null,
      )
    }
  }
  when (val tempData = data) {
    is ImagesList.ViewModel.Data.Loading -> ImagesListLoading()
    is ImagesList.ViewModel.Data.Empty -> ImagesListEmpty()
    is ImagesList.ViewModel.Data.Results -> ImagesListResult(tempData.images)
  }
}

@Composable
private fun ImagesListLoading() {

}

@Composable
private fun ImagesListEmpty() {

}

@Composable
private fun ImagesListResult(
  images: List<ImageListItem>
) {

}