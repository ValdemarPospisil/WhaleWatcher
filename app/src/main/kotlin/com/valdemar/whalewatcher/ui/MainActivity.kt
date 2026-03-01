package com.valdemar.whalewatcher.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.valdemar.whalewatcher.ui.navigation.BottomNavigationBar
import com.valdemar.whalewatcher.ui.navigation.Screen
import com.valdemar.whalewatcher.ui.screens.HomeScreen
import com.valdemar.whalewatcher.ui.screens.LibraryScreen
import com.valdemar.whalewatcher.ui.screens.ListDetailsScreen
import com.valdemar.whalewatcher.ui.screens.SearchScreen
import com.valdemar.whalewatcher.ui.theme.WhaleWatcherTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    // We keep the viewModel injected for future use, though we don't need it for scaffolding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WhaleWatcherTheme {
                WhaleWatcherApp()
            }
        }
    }
}

@Composable
fun WhaleWatcherApp() {
    val navController = rememberNavController()
    // Determine whether to show the bottom bar based on the current route
    val currentBackStackEntry = navController.currentBackStackEntryAsState().value
    val currentRoute = currentBackStackEntry?.destination?.route

    // Hide bottom bar on secondary screens like ListDetails
    val showBottomBar = currentRoute in listOf(
        Screen.Home.route,
        Screen.Search.route,
        Screen.Library.route
    )

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                BottomNavigationBar(navController = navController)
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Home.route) {
                HomeScreen(
                    onNavigateToList = { listName ->
                        navController.navigate(Screen.ListDetails.createRoute(listName))
                    }
                )
            }
            composable(Screen.Search.route) {
                SearchScreen()
            }
            composable(Screen.Library.route) {
                LibraryScreen(
                    onNavigateToList = { listName ->
                        navController.navigate(Screen.ListDetails.createRoute(listName))
                    }
                )
            }
            composable(
                route = Screen.ListDetails.route,
                arguments = listOf(navArgument("listName") { type = NavType.StringType })
            ) { backStackEntry ->
                val listName = backStackEntry.arguments?.getString("listName") ?: ""
                ListDetailsScreen(
                    listName = listName,
                    onNavigateBack = { navController.popBackStack() }
                )
            }
        }
    }
}
