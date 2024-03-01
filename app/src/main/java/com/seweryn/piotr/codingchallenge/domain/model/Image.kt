package com.seweryn.piotr.codingchallenge.domain.model

data class Image(
  val id: Long,
  val previewURL: String,
  val largeImageURL: String,
  val user: String,
  val likes:  Int,
  val comments: Int,
  val downloads: Long,
  val tags: List<String>
)