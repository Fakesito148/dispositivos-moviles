package com.example.dismov.ui.user

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.*
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserProfileScreen(onLogout: () -> Unit) {
    Scaffold(
        topBar = { TopAppBar(title = { Text("Perfil del Usuario") }) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Button(onClick = onLogout, modifier = Modifier.fillMaxWidth()) {
                Text("Cerrar Sesión")
            }
        }
    }
}
