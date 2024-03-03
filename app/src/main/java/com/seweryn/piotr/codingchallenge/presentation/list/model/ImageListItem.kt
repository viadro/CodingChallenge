package com.seweryn.piotr.codingchallenge.presentation.list.model

data class ImageListItem(
  val userName: String,
  val thumbnailUrl: String,
  val tags: List<String>,
  val onClick: () -> Unit,
)