package com.alexru.dreamingmachine

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.alexru.dreamingmachine.ui.theme.DreamingMachineTheme
import com.alexru.presentation.AppNavHost
import dagger.hilt.android.AndroidEntryPoint

/**
 * Main entry point of app
 * Calls [AppNavHost] function in presentation library
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DreamingMachineTheme {
                AppNavHost()
            }
        }
    }
}