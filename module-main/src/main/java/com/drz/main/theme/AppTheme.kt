package com.drz.main.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val LightColors = lightColorScheme(
    primary = Color(0xFF1A1A1A),
    secondary = Color(0xFF5C5C5C),
    background = Color(0xFFFFFFFF),
    surface = Color(0xFFF5F5F5),
)

private val DarkColors = darkColorScheme(
    primary = Color(0xFFFFFFFF),
    secondary = Color(0xFFB0B0B0),
    background = Color(0xFF121212),
    surface = Color(0xFF1E1E1E),
)

@Composable
fun AppTheme(
    darkTheme: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColors else LightColors
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as? android.app.Activity)?.window ?: return@SideEffect
            // 浅色主题时状态栏用深色图标，深色主题时用浅色图标
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }
    MaterialTheme(
        colorScheme = colorScheme,
        content = content
    )
}
