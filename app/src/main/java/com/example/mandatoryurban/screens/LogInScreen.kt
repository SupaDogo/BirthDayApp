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
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.graphics.colorspace.ColorSpace
import androidx.compose.ui.graphics.colorspace.Rgb
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.HapticFeedbackConstantsCompat
import androidx.navigation.NavHostController
import com.example.mandatoryurban.MainActivity
import com.example.mandatoryurban.NavRoutes
import com.example.mandatoryurban.R
import com.example.mandatoryurban.auth.AuthRepo
import com.example.mandatoryurban.auth.AuthViewModel

val authViewModel = AuthViewModel()
val provider = GoogleFont.Provider(
    providerAuthority = "com.google.android.gms.fonts",
    providerPackage = "com.google.android.gms",
    certificates = R.array.com_google_android_gms_fonts_certs
)
val fontName = GoogleFont("Montserrat")

val fontFamily = FontFamily(
    Font(googleFont = fontName, fontProvider = provider)
)


@Composable
fun Login(onNavigateToRegister: ()->Unit,onNavigateToList: (userName:String)->Unit) {
    Scaffold(){ innerPadding ->
        LogInScreen(modifier = Modifier.padding(innerPadding), navigateToRegister =  onNavigateToRegister,navigateToList = onNavigateToList)
    }
}



@Composable
fun LogInScreen(modifier: Modifier, navigateToRegister: () -> Unit, navigateToList: (userName:String) -> Unit){

    var loginColor by remember { mutableStateOf(tealColor) }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = modifier.fillMaxSize().padding(10.dp)
        ) {
            Text(
                text = "Log In",
                fontSize = 30.sp,
                fontFamily = fontFamily,
                fontWeight = FontWeight.SemiBold
                )
            var userName by rememberSaveable { mutableStateOf("") }
            var password by rememberSaveable { mutableStateOf("") }
            OutlinedTextField(
                value = userName,
                onValueChange = { userName = it },
                label = { Text(text = "Username",
                            fontFamily = fontFamily) }
            )
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text(text = "Password",
                    fontFamily = fontFamily) },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.padding(10.dp)
            ) {
                Button(onClick = {
                    authViewModel.signIn(userName, password, onSuccess = {MainActivity.giveUser(userName)
                        navigateToList(userName)}, onFailure = {loginColor = Color.Red})
                    },
                        enabled = userName.isNotBlank() && password.isNotBlank(),
                    colors = ButtonDefaults.buttonColors(containerColor = loginColor)) {
                    Text(text = "Log In",
                        fontFamily = fontFamily)
                }
                Button(onClick = { navigateToRegister() },
                    colors = ButtonDefaults.buttonColors(containerColor = tealColor)) {
                    Text(text = "Register",
                        fontFamily = fontFamily)

                }
            }
        }

}

@Preview(showBackground = true)
@Composable
fun LogInPreview(){
    LogInScreen(Modifier, {},{})
}
