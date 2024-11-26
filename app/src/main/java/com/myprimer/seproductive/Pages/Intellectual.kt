package com.myprimer.seproductive.Pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.myprimer.seproductive.R


@Composable
fun Intellectual(navController: NavController, modifier: Modifier = Modifier, viewModel: TodoViewModel) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF1CB5E0))  // Fondo negro sin transparencia
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Juego 1: Acertijos lógicos
        IntellectualGameItem(
            imageRes = R.drawable.puzzles2,
            description = "¡Desafía tu mente con cultura general!",
            onClick = {
                //    navController.navigate(Screen.AcertijoGame.route)
            }
        )

        // Juego 2: Cálculos mentales
        IntellectualGameItem(
            imageRes = R.drawable.math1,
            description = "¡Desafía tu mente y agudiza tus habilidades de cálculo!",
            onClick = {
                //    navController.navigate(Screen.Matematicas.route)// Navigate to Math Game
            }
        )

        // Juego 3: Agregar palabras aprendidas
        IntellectualGameItem(
            imageRes = R.drawable.idiomas,
            description ="¡Domina el idioma La repetición espaciada te ayudará a retenerlas para siempre!",
            onClick = {
                //          navController.navigate(Screen.LanguageGame.route)// Navigate to Language Game
            }
        )
    }
}



@Composable
fun IntellectualGameItem(imageRes: Int, description: String, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.primary)
            .clickable(onClick = onClick)
            .padding(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = imageRes),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .padding(bottom = 8.dp),
            contentScale = ContentScale.Fit
        )
        Text(
            text = description,
            fontSize = 14.sp,
            color = Color.Yellow
        )
    }
}
