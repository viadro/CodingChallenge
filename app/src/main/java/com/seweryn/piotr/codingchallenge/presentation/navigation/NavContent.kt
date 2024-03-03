package com.seweryn.piotr.codingchallenge.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun NavContent() {
  val navController = rememberNavController()
  NavHost(
    navController = navController,
    startDestination = Destination.List.route,
  ) {
    composable(Destination.List.route) {

    }
    composable(Destination.Details.route) {

    }
  }
}