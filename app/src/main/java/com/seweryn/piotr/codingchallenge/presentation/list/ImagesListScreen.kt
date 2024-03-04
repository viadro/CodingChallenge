package com.seweryn.piotr.codingchallenge.presentation.list

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.seweryn.piotr.codingchallenge.R
import com.seweryn.piotr.codingchallenge.presentation.list.model.ImageListItem
import com.seweryn.piotr.codingchallenge.ui.theme.Background
import com.seweryn.piotr.codingchallenge.ui.theme.Purple80
import com.seweryn.piotr.codingchallenge.ui.theme.Typography

@Composable
fun ImagesListScreen(
  viewModel: ImagesList.ViewModel,
) {
  val data by viewModel.state.collectAsStateWithLifecycle()
  Column(
    modifier = Modifier
      .background(Background)
      .padding(16.dp),
  ) {
    Row(
      modifier = Modifier.fillMaxWidth(),
      verticalAlignment = Alignment.CenterVertically,
    ) {
      TextField(
        modifier = Modifier
          .weight(1f),
        value = data.query,
        enabled = data.searchEnabled,
        onValueChange = data.onQueryChanged,
      )
      Image(
        modifier = Modifier
          .padding(start = 8.dp)
          .clickable {
            data.searchAction()
          },
        painter = painterResource(id = R.drawable.ic_search),
        contentDescription = null,
      )
    }
    when (val tempData = data) {
      is ImagesList.ViewModel.Data.Loading -> ImagesListLoading()
      is ImagesList.ViewModel.Data.Empty -> ImagesListEmpty()
      is ImagesList.ViewModel.Data.Results -> ImagesListResult(
        modifier = Modifier.weight(1f),
        images = tempData.images,
      )
    }
  }
}

@Composable
private fun ImagesListLoading() {
  Box(modifier = Modifier.fillMaxSize()) {
    CircularProgressIndicator()
  }
}

@Composable
private fun ImagesListEmpty() {
  Box(modifier = Modifier.fillMaxSize()) {
    Text(
      text = stringResource(id = R.string.no_results),
      style = Typography.bodyLarge,
      color = Color.Black,
    )
  }
}

@Composable
private fun ImagesListResult(
  modifier: Modifier,
  images: List<ImageListItem>,
) {
  LazyColumn(
    modifier = modifier,
    verticalArrangement = Arrangement.spacedBy(16.dp),
  ) {
    items(images) { image ->
      ImageListItem(item = image)
    }
  }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun ImageListItem(
  item: ImageListItem
) {
  Column(
    modifier = Modifier
      .fillMaxWidth()
      .padding(16.dp)
      .background(
        color = Color.White,
        shape = RoundedCornerShape(8.dp),
      )
  ) {
    Text(
      text = item.userName,
      style = Typography.bodyLarge,
      color = Color.Black,
    )
    Spacer(modifier = Modifier.height(8.dp))
    AsyncImage(
      model = item.thumbnailUrl,
      contentDescription = null,
    )
    Spacer(modifier = Modifier.height(8.dp))
    FlowRow {
      item.tags.forEach { tag ->
        Box(
          modifier = Modifier
            .padding(4.dp)
            .background(
              color = Purple80,
              shape = RoundedCornerShape(24.dp)
            ),
        ) {
          Text(
            modifier = Modifier
              .padding(4.dp),
            text = tag,
            style = Typography.labelSmall,
            color = Color.White,
          )
        }
      }
    }
  }
}