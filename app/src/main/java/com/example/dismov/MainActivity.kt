package com.example.dismov

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.dismov.ui.LoginScreen
import com.example.dismov.ui.theme.DisMovTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DisMovTheme {
                LoginScreen()
            }
        }
    }
}
