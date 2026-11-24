package com.example.anagramas.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColors = lightColorScheme(
    primary = Color(0xFF00695C),
    primaryContainer = Color(0xFFB2DFDB),
    onPrimaryContainer = Color(0xFF00251F),
    secondary = Color(0xFF8D5B00),
    secondaryContainer = Color(0xFFFFE0A8),
    onSecondaryContainer = Color(0xFF2C1B00)
)

private val DarkColors = darkColorScheme(
    primary = Color(0xFF80CBC4),
    primaryContainer = Color(0xFF004D43),
    onPrimaryContainer = Color(0xFFB2DFDB),
    secondary = Color(0xFFFFC966),
    secondaryContainer = Color(0xFF664200),
    onSecondaryContainer = Color(0xFFFFE0A8)
)

@Composable
fun AnagramasTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = if (darkTheme) DarkColors else LightColors,
        typography = Typography,
        content = content
    )
}
