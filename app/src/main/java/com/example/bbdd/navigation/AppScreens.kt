package com.example.bbdd.navigation

sealed class AppScreens (val route: String) {
    object Inicio: AppScreens("Inicio")
    object Formulario: AppScreens("Formulario")
    object Resultados: AppScreens("Resultados")
}