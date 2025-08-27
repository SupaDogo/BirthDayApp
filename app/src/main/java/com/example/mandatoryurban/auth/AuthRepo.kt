package com.example.mandatoryurban.auth

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.auth.FirebaseUser

class AuthRepo {
    private val auth = FirebaseAuth.getInstance()

    fun signIn(email: String, password: String, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                onSuccess()
            } else {
                onFailure(task.exception?: Exception("Wrong login"))
            }
        }


    }
    fun signUp(
        email: String,
        password: String,
        onSuccess: (FirebaseUser) -> Unit,
        onFailure: (String) -> Unit
    ) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener { authResult ->
                authResult.user?.let(onSuccess)
            }
            .addOnFailureListener { e ->
                val errorMsg = when (e) {
                    is FirebaseAuthUserCollisionException -> "Email already exists"
                    is FirebaseAuthWeakPasswordException -> "Password too weak"
                    else -> "Registration failed: ${e.message}"
                }
                onFailure(errorMsg)
            }
    }
    fun signOut() {
        auth.signOut()
    }



}