package com.alexru.dreamingmachine

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.alexru.dreamingmachine.ui.theme.DreamingMachineTheme
import com.alexru.presentation.navigation.AppNavHost
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