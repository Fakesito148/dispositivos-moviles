// ui/components/BottomNavBar.kt
package com.example.dismov.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import com.example.dismov.navigation.AppRoutes

data class BottomNavItem(val route: String, val icon: ImageVector, val label: String)

@Composable
fun BottomNavBar(navController: NavController, currentRoute: String) {
    val items = listOf(
        BottomNavItem(AppRoutes.INVENTORY, Icons.Default.Inventory, "Inventario"),
        BottomNavItem(AppRoutes.HISTORY, Icons.Default.History, "Historial"),
        BottomNavItem(AppRoutes.PROFILE, Icons.Default.Person, "Perfil")
    )

    NavigationBar {
        items.forEach { item ->
            NavigationBarItem(
                selected = currentRoute == item.route,
                onClick = {
                    if (currentRoute != item.route) {
                        navController.navigate(item.route)
                    }
                },
                icon = { Icon(item.icon, contentDescription = item.label) },
                label = { Text(item.label) }
            )
        }
    }
}
