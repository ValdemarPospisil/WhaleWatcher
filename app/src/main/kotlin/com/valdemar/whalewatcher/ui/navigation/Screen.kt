package com.valdemar.whalewatcher.ui.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String, @StringRes val resourceId: Int? = null, val icon: ImageVector? = null) {
    object Home : Screen("home", android.R.string.untitled, Icons.Filled.Home) // We will replace string res later
    object Search : Screen("search", android.R.string.search_go, Icons.Filled.Search)
    object Library : Screen("library", android.R.string.copy, Icons.Filled.List)
    
    // Secondary screens
    object ListDetails : Screen("list_details/{listName}") {
        fun createRoute(listName: String) = "list_details/${android.net.Uri.encode(listName)}"
    }
}

val BottomNavScreens = listOf(
    Screen.Home,
    Screen.Search,
    Screen.Library
)
