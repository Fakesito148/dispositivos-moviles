package com.example.dismov.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.dismov.ui.user.AvailableToolsScreen
import com.example.dismov.ui.user.MyToolsScreen
import com.example.dismov.ui.user.UserProfileScreen

@Composable
fun UserNavigation(onLogout: () -> Unit) {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = navController.currentBackStackEntry?.destination?.route == "available",
                    onClick = { navController.navigate("available") },
                    label = { Text("Disponibles") },
                    icon = { Icon(Icons.Default.Home, contentDescription = null) }
                )
                NavigationBarItem(
                    selected = navController.currentBackStackEntry?.destination?.route == "mytools",
                    onClick = { navController.navigate("mytools") },
                    label = { Text("Mis Herramientas") },
                    icon = { Icon(Icons.Default.List, contentDescription = null) }
                )
                NavigationBarItem(
                    selected = navController.currentBackStackEntry?.destination?.route == "userprofile",
                    onClick = { navController.navigate("userprofile") },
                    label = { Text("Perfil") },
                    icon = { Icon(Icons.Default.Person, contentDescription = null) }
                )
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "available",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("available") { AvailableToolsScreen() }
            composable("mytools") { MyToolsScreen() }
            composable("userprofile") { UserProfileScreen(onLogout = onLogout) }
        }
    }
}
