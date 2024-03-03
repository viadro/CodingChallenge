package com.seweryn.piotr.codingchallenge.presentation.navigation

sealed class Destination(val route: String) {
  data object List : Destination("images/list")
  data object Details : Destination("images/details/$DETAILS_ID")

  companion object {
    const val DETAILS_ID = "ID"
  }
}