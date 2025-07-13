package io.jadu.restrobanc.restrobanc.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.assignonebanc.ui.screens.CartScreen
import com.example.assignonebanc.ui.screens.CuisineScreen
import com.example.assignonebanc.ui.screens.HomeScreen
import com.example.assignonebanc.viewmodel.RestaurantViewModel

@Composable
fun RestaurantNavigation(
    navController: NavHostController = rememberNavController(),
    viewModel: RestaurantViewModel = viewModel()
) {
    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable("home") {
            HomeScreen(
                viewModel = viewModel,
                onCuisineClick = { cuisineId ->
                    navController.navigate("cuisine/$cuisineId")
                },
                onCartClick = {
                    navController.navigate("cart")
                }
            )
        }
        
        composable("cuisine/{cuisineId}") { backStackEntry ->
            val cuisineId = backStackEntry.arguments?.getString("cuisineId") ?: ""
            CuisineScreen(
                viewModel = viewModel,
                cuisineId = cuisineId,
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
        
        composable("cart") {
            CartScreen(
                viewModel = viewModel,
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}
