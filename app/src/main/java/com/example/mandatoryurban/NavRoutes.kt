package com.example.mandatoryurban

sealed class NavRoutes(val route: String) {
    data object Login : NavRoutes("login")
    data object Register : NavRoutes("register")
    data object List : NavRoutes("list")

}