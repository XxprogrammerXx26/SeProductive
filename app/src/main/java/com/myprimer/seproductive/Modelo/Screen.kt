package com.myprimer.seproductive.Modelo


sealed class Screen(val route: String) {

    object  MenuPrincipal : Screen("menu_principal")


    object  Profile : Screen("profile")


    object Matematicas : Screen("matematicas")
    //   object LanguageGame : Screen("languageGame")



}
