package com.seweryn.piotr.codingchallenge.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.seweryn.piotr.codingchallenge.presentation.details.ImageDetailsScreen
import com.seweryn.piotr.codingchallenge.presentation.details.ImageDetailsViewModel
import com.seweryn.piotr.codingchallenge.presentation.list.ImagesList
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
      NavEvent(navEvent = viewModel.navAction) { action ->
        when (action) {
          is ImagesList.Navigation.Action.ImageDetails -> navController.navigate(
            Destination.Details(id = action.id).route
          )
        }
      }
      ImagesListScreen(viewModel = viewModel)
    }
    composable(
      route = Destination.Details().route,
      arguments = listOf(
        navArgument(Destination.DETAILS_ID) {
          type = NavType.LongType
        }
      )
    ) {
      val viewModel = hiltViewModel<ImageDetailsViewModel>()
      ImageDetailsScreen(viewModel = viewModel)
    }
  }
}