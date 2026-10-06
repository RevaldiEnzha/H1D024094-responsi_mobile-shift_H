package com.pemmob.responsi.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary = BluePrimary,
    onPrimary = Color.White,
    primaryContainer = BlueContainer,
    onPrimaryContainer = OnBlueContainer,
    secondary = TealSecondary,
    onSecondary = Color.White,
    background = LightBackground,
    onBackground = Color(0xFF1A1C1E),
    surface = LightSurface,
    onSurface = Color(0xFF1A1C1E),
    surfaceVariant = Color(0xFFE1E2EC),
    onSurfaceVariant = Color(0xFF44474F)
)

private val DarkColorScheme = darkColorScheme(
    primary = DarkPrimary,
    onPrimary = DarkOnPrimary,
    primaryContainer = DarkBlueContainer,
    onPrimaryContainer = BlueContainer,
    secondary = DarkTeal,
    onSecondary = Color(0xFF00201C),
    background = DarkBackground,
    onBackground = DarkOnSurface,
    surface = DarkSurface,
    onSurface = DarkOnSurface,
    surfaceVariant = Color(0xFF2D3138),
    onSurfaceVariant = Color(0xFFC3C6CF)
)

@Composable
fun ResponsiTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}