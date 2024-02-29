package com.seweryn.piotr.codingchallenge.data.model

data class ImageResponse(
  val id: Long,
  val previewURL: String,
  val largeImageURL: String,
  val user: String,
  val likes:  Int,
  val comments: Int,
  val downloads: Long,
  val tags: String
)