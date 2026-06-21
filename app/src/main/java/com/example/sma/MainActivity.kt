package com.example.sma

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.sma.ui.theme.CineLogTheme

/**
 * Activity principal da aplicação.
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CineLogTheme(darkTheme = true) {
                CineLogApp()
            }
        }
    }
}
