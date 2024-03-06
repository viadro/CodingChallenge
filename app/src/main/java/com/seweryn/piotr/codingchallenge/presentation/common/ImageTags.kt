package com.seweryn.piotr.codingchallenge.presentation.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.seweryn.piotr.codingchallenge.ui.theme.Typography

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ImageTags(tags: List<String>) {
  FlowRow {
    tags.forEach { tag ->
      Box(
        modifier = Modifier
          .padding(4.dp)
          .background(
            color = MaterialTheme.colorScheme.primary,
            shape = RoundedCornerShape(24.dp)
          ),
      ) {
        Text(
          modifier = Modifier
            .padding(8.dp),
          text = tag,
          style = Typography.labelSmall,
          color = MaterialTheme.colorScheme.onPrimary,
        )
      }
    }
  }
}