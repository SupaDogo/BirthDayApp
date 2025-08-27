package com.example.mandatoryurban.auth

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser

class AuthViewModel: ViewModel() {
    private val authRepo = AuthRepo()
    fun signIn(email: String, password: String, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        authRepo.signIn(email, password, onSuccess, onFailure)
    }
    fun signUp(
        email: String,
        password: String,
        onSuccess: (FirebaseUser) -> Unit,
        onFailure: (String) -> Unit){
        authRepo.signUp(email, password, onSuccess, onFailure)

    }

    fun signOut() {
        authRepo.signOut()
    }



}