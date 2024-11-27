package com.myprimer.seproductive.Modelo


sealed class Screen(val route: String) {

    object  MenuPrincipal : Screen("menu_principal")


    object  Profile : Screen("profile")

    object  Cultura : Screen("cultura_general")
  //  object Matematicas : Screen("matematicas")
    //   object LanguageGame : Screen("languageGame")



}
