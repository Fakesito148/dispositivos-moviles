// navigation/AdminNavigation.kt
package com.example.dismov.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import com.example.dismov.ui.admin.HistoryScreen
import com.example.dismov.ui.admin.InventoryScreen
import com.example.dismov.ui.admin.ProfileScreen
import com.example.dismov.ui.components.BottomNavBar

@Composable
fun AdminNavigation(navController: NavHostController, onLogout: () -> Unit) {
    val adminNavController = rememberNavController()
    val currentRoute = adminNavController.currentBackStackEntryAsState().value?.destination?.route ?: AppRoutes.INVENTORY

    Scaffold(
        bottomBar = {
            BottomNavBar(navController = adminNavController, currentRoute = currentRoute)
        }
    ) { padding ->
        NavHost(
            navController = adminNavController,
            startDestination = AppRoutes.INVENTORY,
            modifier = Modifier.padding(padding)
        ) {
            composable(AppRoutes.INVENTORY) { InventoryScreen() }
            composable(AppRoutes.HISTORY) { HistoryScreen() }
            composable(AppRoutes.PROFILE) {
                ProfileScreen(
                    onLogout = {
                        onLogout()
                    },
                    onRegisterUser = { navController.navigate(AppRoutes.REGISTER) // ✅ Esto es lo que hace que funcione
                        // Aquí más adelante podrías navegar a RegisterUserScreen
                    }
                )
            }
        }
    }
}
