package com.example.dismov.ui.user

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyToolsScreen() {
    Scaffold(
        topBar = { TopAppBar(title = { Text("Mis Herramientas") }) }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
        ) {
            Text("Aquí van tus herramientas")
        }
    }
}
