package com.seweryn.piotr.codingchallenge.presentation.navigation

sealed class Destination(val route: String) {
  data object List : Destination("images/list")
  data class Details(val id: Long? = null) : Destination("images/details/${id ?: DETAILS_ID}")

  companion object {
    const val DETAILS_ID = "ID"
  }
}