package com.valdemar.whalewatcher.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DeepOceanColorScheme = darkColorScheme(
    primary = DeepOceanPrimary,
    secondary = DeepOceanSecondary,
    background = DeepOceanBackground,
    surface = DeepOceanSurface,
    onPrimary = DeepOceanText,
    onSecondary = DeepOceanBackground,
    onBackground = DeepOceanText,
    onSurface = DeepOceanText,
    error = ErrorRed,
    onError = DeepOceanText,
    surfaceVariant = DeepOceanSurface,
    onSurfaceVariant = DeepOceanTextSecondary
)

// We force the dark theme for the "Deep Ocean" aesthetic regardless of system settings for now
@Composable
fun WhaleWatcherTheme(
    darkTheme: Boolean = true, // Force dark theme by default
    content: @Composable () -> Unit
) {
    val colorScheme = DeepOceanColorScheme
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.background.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = false
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        content = content
    )
}
