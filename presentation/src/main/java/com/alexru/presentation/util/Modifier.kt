package com.alexru.presentation.util

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.composed

fun Modifier.selectedBackground(isSelected: Boolean): Modifier = if (isSelected) {
    composed {
        val alpha = if (isSystemInDarkTheme()) 0.16f else 0.22f
        val color = MaterialTheme.colorScheme.secondary.copy(alpha = alpha)
        Modifier.drawBehind {
            drawRect(color)
        }
    }
} else {
    this
}