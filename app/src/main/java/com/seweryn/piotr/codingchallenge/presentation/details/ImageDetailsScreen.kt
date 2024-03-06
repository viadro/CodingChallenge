package com.seweryn.piotr.codingchallenge.presentation.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.seweryn.piotr.codingchallenge.R
import com.seweryn.piotr.codingchallenge.presentation.common.ImageTags
import com.seweryn.piotr.codingchallenge.presentation.error.ErrorComponent
import com.seweryn.piotr.codingchallenge.ui.theme.Typography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImageDetailsScreen(
  viewModel: ImageDetails.ViewModel
) {
  val data by viewModel.state.collectAsStateWithLifecycle()
  Scaffold(
    topBar = {
      TopAppBar(
        title = {
          Text(
            text = stringResource(id = R.string.details_title),
            style = Typography.bodyLarge,
            color = MaterialTheme.colorScheme.onBackground,
          )
        }
      )
    }
  ) { paddingValues ->
    Column(
      modifier = Modifier
        .padding(paddingValues)
        .background(MaterialTheme.colorScheme.background)
        .fillMaxSize(),
    ) {
      when (val tempData = data) {
        is ImageDetails.ViewModel.Data.Image ->
          ImageDetailsContent(data = tempData)

        is ImageDetails.ViewModel.Data.Error ->
          ErrorComponent(data = tempData.data)

        else -> {}
      }
    }

  }
}

@Composable
private fun ImageDetailsContent(
  data: ImageDetails.ViewModel.Data.Image,
) {
  AsyncImage(
    modifier = Modifier.fillMaxWidth(),
    model = data.imageUrl,
    contentDescription = null,
  )
  DetailsRow(
    label = stringResource(id = R.string.details_user),
    value = data.userName,
  )
  DetailsRow(
    label = stringResource(id = R.string.details_comments),
    value = data.comments,
  )
  DetailsRow(
    label = stringResource(id = R.string.details_likes),
    value = data.likes,
  )
  DetailsRow(
    label = stringResource(id = R.string.details_downloads),
    value = data.downloads,
  )
  ImageTags(tags = data.tags)
}

@Composable
private fun DetailsRow(
  label: String,
  value: String,
) {
  Row(
    modifier = Modifier.padding(8.dp),
    verticalAlignment = Alignment.CenterVertically,
  ) {
    Text(
      text = label,
      style = Typography.bodyLarge,
      color = MaterialTheme.colorScheme.onBackground,
    )
    Spacer(modifier = Modifier.width(4.dp))
    Text(
      text = value,
      style = Typography.labelMedium,
      color = MaterialTheme.colorScheme.onBackground,
    )
  }
}