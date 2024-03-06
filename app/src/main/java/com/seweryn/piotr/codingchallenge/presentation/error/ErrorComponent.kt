package com.seweryn.piotr.codingchallenge.presentation.error

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import com.seweryn.piotr.codingchallenge.R
import com.seweryn.piotr.codingchallenge.presentation.error.model.ErrorData
import com.seweryn.piotr.codingchallenge.ui.theme.Typography

@Composable
fun ErrorComponent(
  data: ErrorData,
) {
  Column(
    modifier = Modifier.fillMaxSize(),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.Center,
  ) {
    Image(
      painter = painterResource(id = R.drawable.ic_error),
      colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.error),
      contentDescription = null,
    )
    Text(
      text = data.message,
      style = Typography.bodyLarge,
      color = MaterialTheme.colorScheme.onBackground,
    )
  }
}