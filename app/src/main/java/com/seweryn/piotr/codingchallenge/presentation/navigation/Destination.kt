package com.seweryn.piotr.codingchallenge.presentation.navigation

import com.seweryn.piotr.codingchallenge.presentation.navigation.Destination.List.createNavigationParameter

sealed class Destination(val route: String) {
  data object List : Destination("images/list")
  data class Details(val id: Long? = null) :
    Destination("images/details/${id.createNavigationParameter(DETAILS_ID)}")

  companion object {
    const val DETAILS_ID = "ID"
  }

  fun Any?.createNavigationParameter(name: String): String =
    this?.let { "$it" } ?: "{$name}"
}