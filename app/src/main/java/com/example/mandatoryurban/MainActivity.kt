package com.example.mandatoryurban

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mandatoryurban.Model.Person
import com.example.mandatoryurban.screens.ListOfBday
import com.example.mandatoryurban.screens.Login
import com.example.mandatoryurban.screens.Register
import com.example.mandatoryurban.ui.theme.MandatoryUrbanTheme

private var currentUser:String = ""
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MandatoryUrbanTheme {
                MainScreen()
            }
        }
    }



    companion object {

        fun giveUser(user:String){
            currentUser = user
        }
    }
}

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    NavHost(navController = navController,
            startDestination = NavRoutes.Login.route){
        composable(NavRoutes.Login.route){
            Login(onNavigateToRegister = { navController.navigate(NavRoutes.Register.route) },
                  onNavigateToList = { navController.navigate(NavRoutes.List.route)})
        }

        composable(NavRoutes.Register.route){
            Register(
                onNavigateBack = { navController.popBackStack() },
                onNavigateToList = { navController.navigate(NavRoutes.List.route) }

            )
        }

        composable(NavRoutes.List.route){
            ListOfBday(onNavigateBack = { navController.popBackStack() },userName = currentUser)
        }
    }
}


