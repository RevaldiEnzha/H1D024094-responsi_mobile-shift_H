package com.pemmob.responsi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.pemmob.responsi.ui.screen.DetailScreen
import com.pemmob.responsi.ui.screen.HomeScreen
import com.pemmob.responsi.ui.theme.ResponsiTheme
import com.pemmob.responsi.ui.viewmodel.BookViewModel


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ResponsiTheme {
                val navController = rememberNavController()
                // Satu ViewModel dipakai bersama oleh Home dan Detail
                val bookViewModel: BookViewModel = viewModel()

                NavHost(navController = navController, startDestination = "home") {
                    composable("home") {
                        HomeScreen(
                            viewModel = bookViewModel,
                            onBookClick = { index -> navController.navigate("detail/$index") }
                        )
                    }
                    composable(
                        route = "detail/{index}",
                        arguments = listOf(navArgument("index") { type = NavType.IntType })
                    ) { backStackEntry ->
                        val index = backStackEntry.arguments?.getInt("index") ?: 0
                        DetailScreen(
                            bookIndex = index,
                            viewModel = bookViewModel,
                            onBackClick = { navController.popBackStack() }
                        )
                    }
                }
            }
        }
    }
}