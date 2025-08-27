package com.example.mandatoryurban.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.mandatoryurban.MainActivity
import com.example.mandatoryurban.NavRoutes
import com.example.mandatoryurban.auth.AuthViewModel


@Composable
fun Register(onNavigateBack: ()->Unit, onNavigateToList: ()->Unit) {
    Scaffold{ innerPadding ->
        RegisterScreen(modifier = Modifier.padding(innerPadding), navigateBack = onNavigateBack,navigateToList = onNavigateToList)
    }
}

@Composable
fun RegisterScreen(modifier: Modifier = Modifier,navigateBack: ()->Unit, navigateToList: ()->Unit){
var registerMessage by rememberSaveable { mutableStateOf("Register") }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = modifier.fillMaxSize().padding(10.dp)
        ) {
            Text(
                text = registerMessage,
                fontSize = 30.sp,
                fontFamily = fontFamily,
                fontWeight = FontWeight.SemiBold
            )
            var password by rememberSaveable { mutableStateOf("") }
            var confirmPassword by rememberSaveable { mutableStateOf("") }
            var email by rememberSaveable { mutableStateOf("") }
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text(text = "E-mail",
                    fontFamily = fontFamily) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
            )
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text(text = "Password",
                    fontFamily = fontFamily) },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )
            OutlinedTextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                label = { Text(text = "Confirm Password",
                    fontFamily = fontFamily) },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.padding(10.dp)
            ) {
                Button(onClick = { authViewModel.signUp(email, password, onSuccess = { MainActivity.giveUser(email)
                    navigateToList() }, onFailure = {registerMessage = it})
                     },
                    enabled = password.isNotBlank() && email.isNotBlank() && confirmPassword==password,
                    colors = ButtonDefaults.buttonColors(containerColor = tealColor)) {
                    Text(text = "Register",
                        fontFamily = fontFamily)
                }
                Button(onClick = { navigateBack() },
                    colors = ButtonDefaults.buttonColors(containerColor = tealColor)) {
                    Text(text = "Back",
                        fontFamily = fontFamily)

                }
            }
        }
    }


@Preview(showBackground = true)
@Composable
fun RegisterPreview(){
    RegisterScreen(Modifier,{},{})
}