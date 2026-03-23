package com.drz.main.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

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
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = LightColors,
        content = content
    )
}
