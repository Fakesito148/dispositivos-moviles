package com.example.dismov

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.dismov.navigation.AppRoutes
import com.example.dismov.navigation.AdminNavigation
import com.example.dismov.navigation.UserNavigation
import com.example.dismov.ui.LoginScreen
import com.example.dismov.ui.admin.RegisterScreen
import com.example.dismov.ui.theme.DisMovTheme
import com.example.dismov.utils.TokenManager

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
                                } else if (role == "user") {
                                    navController.navigate(AppRoutes.USER)
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

                    composable(AppRoutes.USER) {
                        UserNavigation(
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
                                navController.popBackStack()
                            }
                        )
                    }
                }
            }
        }
    }
}
