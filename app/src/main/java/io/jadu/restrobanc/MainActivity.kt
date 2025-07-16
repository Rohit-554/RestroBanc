package io.jadu.restrobanc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.media3.exoplayer.ExoPlayer
import io.jadu.restrobanc.restrobanc.navigation.App
import io.jadu.restrobanc.restrobanc.ui.screens.Screen
import io.jadu.restrobanc.restrobanc.ui.screens.StreamContent
import io.jadu.restrobanc.ui.theme.RestroBancTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            RestroBancTheme {
                Surface (
                    modifier = Modifier.fillMaxSize()
                ) {
                    StreamContent()
                }
            }
        }
    }
}
