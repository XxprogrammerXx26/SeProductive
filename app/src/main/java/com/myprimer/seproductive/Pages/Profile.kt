package com.myprimer.seproductive.Pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.myprimer.seproductive.Modelo.LoginViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    modifier: Modifier,
    viewModel: LoginViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    navController: NavHostController
) {
    val context = LocalContext.current
    val googleSignInClient = remember {
        GoogleSignIn.getClient(
            context,
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build()
        )
    }

    // Obtén el usuario actual de Firebase
    val user = FirebaseAuth.getInstance().currentUser

    // Aquí vamos a obtener el estado de autenticación desde el ViewModel
    val isUserLoggedOut by viewModel.isLoggedOut.collectAsState()

    Column(modifier = modifier.fillMaxSize()) {
        // Barra de navegación (Navbar)
        TopAppBar(
            title = { Text("Configuración", fontSize = 20.sp, fontWeight = FontWeight.Bold) },
            colors = TopAppBarDefaults.smallTopAppBarColors(
                containerColor = MaterialTheme.colorScheme.primary, // Fondo del AppBar
                titleContentColor = MaterialTheme.colorScheme.onPrimary // Color del título
            )
        )

        // Contenido del perfil
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            // Foto de perfil (si existe)
            if (user?.photoUrl != null) {
                Image(
                    painter = rememberImagePainter(user.photoUrl),
                    contentDescription = "Foto de perfil",
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape)
                        .border(2.dp, MaterialTheme.colorScheme.primary, CircleShape)
                )
            } else {
                // Si no hay foto de perfil, mostrar un ícono genérico
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = "Icono de usuario",
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape)
                        .border(2.dp, MaterialTheme.colorScheme.primary, CircleShape),
                    tint = MaterialTheme.colorScheme.primary
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Nombre y correo del usuario
            if (user != null) {
                Text(
                    text = "Hola, ${user.displayName ?: "Usuario"}!",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    text = user.email ?: "Correo no disponible",
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onBackground
                )
            } else {
                Text(
                    text = "No hay usuario autenticado",
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Botón de cerrar sesión
            Button(
                onClick = {
                    // Lógica de cierre de sesión con Google
                    googleSignInClient.signOut().addOnCompleteListener {
                        println("Sesión cerrada con Google")
                        // Lógica para cerrar sesión desde Firebase
                        FirebaseAuth.getInstance().signOut()
                        println("Sesión cerrada en Firebase")

                        // Llamar al viewModel para actualizar el estado de la sesión
                        viewModel.signOut()

                        // Aquí navegas al login después de cerrar sesión
                        navController.navigate("login") {
                            // Limpiar el stack de navegación
                            popUpTo("menu_principal") { inclusive = true }
                        }
                    }
                },
                colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.secondary),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .width(200.dp)
                    .height(48.dp)
            ) {
                Text(text = "Cerrar Sesión", color = MaterialTheme.colorScheme.onSecondary)
            }

            // Botón de borrar cuenta
            Button(
                onClick = {
                    // Lógica para borrar la cuenta desde Firebase
                    user?.delete()?.addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            println("Cuenta borrada con éxito")
                            // Llamar al viewModel para actualizar el estado de la sesión
                            viewModel.signOut()

                            // Navegar a LoginScreen después de borrar la cuenta
                            navController.navigate("login") {
                                popUpTo("menu_principal") { inclusive = true }
                            }
                        } else {
                            // Manejar el error, mostrar mensaje
                            println("Error al borrar la cuenta: ${task.exception?.message}")
                        }
                    }
                },
                colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.error),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .width(200.dp)
                    .height(48.dp)
                    .padding(top = 16.dp) // Espaciado entre botones
            ) {
                Text(text = "Borrar Cuenta", color = Color.White)
            }

            // Si el usuario ya ha cerrado sesión, muestra un mensaje o redirige automáticamente.
            if (isUserLoggedOut) {
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    text = "Has cerrado sesión correctamente",
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        }
    }
}
