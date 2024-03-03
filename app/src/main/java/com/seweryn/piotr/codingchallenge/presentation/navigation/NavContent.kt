package com.seweryn.piotr.codingchallenge.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.seweryn.piotr.codingchallenge.presentation.list.ImagesListScreen
import com.seweryn.piotr.codingchallenge.presentation.list.ImagesListViewModel

@Composable
fun NavContent() {
  val navController = rememberNavController()
  NavHost(
    navController = navController,
    startDestination = Destination.List.route,
  ) {
    composable(Destination.List.route) {
      val viewModel = hiltViewModel<ImagesListViewModel>()
      ImagesListScreen(viewModel = viewModel)
    }
    composable(Destination.Details.route) {

    }
  }
}