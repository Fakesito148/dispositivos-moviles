package com.example.dismov

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.dismov.navigation.AppRoutes
import com.example.dismov.navigation.AdminNavigation
import com.example.dismov.ui.LoginScreen
import com.example.dismov.ui.admin.RegisterScreen
import com.example.dismov.ui.theme.DisMovTheme
import com.example.dismov.utils.TokenManager  // IMPORTANTE: importar TokenManager

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inicializar TokenManager aquí para evitar kotlin uninitializedPropertyException
        TokenManager.init(this)

        setContent {
            DisMovTheme {
                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = AppRoutes.LOGIN
                ) {
                    composable(AppRoutes.LOGIN) {
                        LoginScreen(
                            onLoginSuccess = { role ->
                                if (role == "admin") {
                                    navController.navigate(AppRoutes.ADMIN)
                                } else {
                                    // Aquí puedes manejar navegación para el rol "usuario"
                                    // Por ahora solo mostramos el admin
                                }
                            }
                        )
                    }

                    composable(AppRoutes.ADMIN) {
                        AdminNavigation(
                            navController = navController,
                            onLogout = {
                                navController.navigate(AppRoutes.LOGIN) {
                                    popUpTo(0)
                                }
                            }
                        )
                    }

                    composable(AppRoutes.REGISTER) {
                        RegisterScreen(
                            onUserRegistered = {
                                navController.popBackStack() // Regresa al perfil
                            }
                        )
                    }
                }
            }
        }
    }
}
