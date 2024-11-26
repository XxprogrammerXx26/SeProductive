package com.myprimer.seproductive

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier


@Composable
fun MenuPrincipal(
    navController: NavHostController,   //lo voy a cambiar a NavHostController
    modifier: Modifier = Modifier,
    todoViewModel: TodoViewModel
) {
    // Crea una instancia de NavController

    val navItemList = listOf(

        NavItem("Home", Icons.Default.Home),
        NavItem("Productivo", Icons.Default.CheckCircle),
        NavItem("Intellectual", Icons.Default.Person),
        NavItem("Profile", Icons.Default.AccountCircle),
    )

    var selectedIndex by remember {
        mutableIntStateOf(0)
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            NavigationBar {
                navItemList.forEachIndexed { index, navItem ->
                    NavigationBarItem(
                        selected = selectedIndex == index,
                        onClick = { selectedIndex = index },
                        icon = { Icon(imageVector = navItem.icon, contentDescription = "icon") },
                        label = { Text(text = navItem.label) }
                    )
                }
            }
        }
    ) { innerPadding ->
        ContentScreen(
            navController = navController,  //aqui otro error
            modifier = Modifier.padding(innerPadding),
            selectedIndex = selectedIndex,
            todoViewModel = todoViewModel
        )
    }
}

@Composable
fun ContentScreen(
    navController: NavHostController,   //Navcontroller
    modifier: Modifier = Modifier,
    selectedIndex: Int,
    todoViewModel: TodoViewModel
) {



    when (selectedIndex) {

        0 -> {
            Home()
        }
        1 -> {
            Productivo(modifier = modifier, viewModel = todoViewModel)
        }
        2 -> {
            Intellectual(navController = navController, modifier = modifier, viewModel = todoViewModel)
        }
        3 -> {

            Profile(modifier = modifier, navController = navController,  viewModel = viewModel())

        }
    }
}

