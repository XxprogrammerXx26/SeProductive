package com.myprimer.seproductive

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import com.myprimer.seproductive.Modelo.TodoViewModel
import com.myprimer.seproductive.ui.theme.SeProductiveTheme




class
MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Obtener el ViewModel
        val todoViewModel = ViewModelProvider(this)[TodoViewModel::class.java]

        enableEdgeToEdge()
        setContent {
            SeProductiveTheme {
                // Crear NavController
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
                        Profile(
                            navController = navController,
                            modifier = Modifier,
                            viewModel = viewModel()
                        )

                    }
                }
            }
        }
    }
}
