package io.jadu.restrobanc.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFFFF6F00), // Deep orange for energy
    onPrimary = Color(0xFF2C1200),
    primaryContainer = Color(0xFFBF360C),
    onPrimaryContainer = Color(0xFFFFCCBC),

    secondary = Color(0xFFFFC107), // Vibrant golden yellow
    onSecondary = Color(0xFF2D2400),
    secondaryContainer = Color(0xFF5D4300),
    onSecondaryContainer = Color(0xFFFFECB3),

    tertiary = Color(0xFF8BC34A), // Fresh green for veggies
    onTertiary = Color(0xFF1A2B00),
    tertiaryContainer = Color(0xFF33691E),
    onTertiaryContainer = Color(0xFFDCEDC8),

    background = Color(0xFF1B1B1B),
    onBackground = Color(0xFFFFF8E1),
    surface = Color(0xFF222222),
    onSurface = Color(0xFFFFF8E1),

    surfaceVariant = Color(0xFF4E342E), // Chocolate-like brown
    onSurfaceVariant = Color(0xFFD7CCC8),

    outline = Color(0xFFA1887F),
    error = Color(0xFFD32F2F),
    onError = Color(0xFFFFEBEE)
)

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFFFF9800), // Bold orange
    onPrimary = Color.White,
    primaryContainer = Color(0xFFFFE0B2),
    onPrimaryContainer = Color(0xFF3E2A00),

    secondary = Color(0xFFFFEB3B), // Bright lemony yellow
    onSecondary = Color(0xFF3C3700),
    secondaryContainer = Color(0xFFFFF9C4),
    onSecondaryContainer = Color(0xFF3E3700),

    tertiary = Color(0xFF4CAF50), // Energetic fresh green
    onTertiary = Color.White,
    tertiaryContainer = Color(0xFFC8E6C9),
    onTertiaryContainer = Color(0xFF003311),

    background = Color(0xFFFFFBF0),
    onBackground = Color(0xFF1C1B1F),
    surface = Color(0xFFFFFBF0),
    onSurface = Color(0xFF1C1B1F),

    surfaceVariant = Color(0xFFD7CCC8), // Light mocha
    onSurfaceVariant = Color(0xFF4E342E),

    outline = Color(0xFF795548),
    error = Color(0xFFD32F2F),
    onError = Color.White
)

@Composable
fun RestroBancTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}