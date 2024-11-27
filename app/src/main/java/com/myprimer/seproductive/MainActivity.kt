package com.myprimer.seproductive


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController



import com.myprimer.seproductive.Modelo.ProfileViewModel
import com.myprimer.seproductive.Modelo.Screen
import com.myprimer.seproductive.Modelo.TodoViewModel
import com.myprimer.seproductive.Pages.CulturaGeneral
import com.myprimer.seproductive.Pages.LoginScreen
import com.myprimer.seproductive.Pages.ProfileScreen

import com.myprimer.seproductive.ui.theme.SeProductiveTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Obtener el ViewModel
        val todoViewModel = ViewModelProvider(this)[TodoViewModel::class.java]

        enableEdgeToEdge()
        setContent {
            SeProductiveTheme {

                val navController = rememberNavController()

                // Configura la navegación con NavHost
                NavHost(navController = navController, startDestination = "login") {
                    // Pantalla de Login
                    composable("login") {
                        LoginScreen(navController = navController)
                    }

                    //Pantalla principal (home)
                    composable("menu_principal") {
                        MenuPrincipal(navController = navController, todoViewModel = todoViewModel)
                    }


                    composable("profile") {
                        ProfileScreen(
                            navController = navController,
                            modifier = Modifier,
                            viewModel = viewModel()
                        )

                    }


                    // Pantalla CulturaGeneral
                    composable(Screen.Cultura.route) {
                        CulturaGeneral()
                    }




                }
            }
        }
    }
}
