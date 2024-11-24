package com.example.myapplication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp

@Composable
fun LoginScreen(
    onLogin: () -> Unit,
    onSignUpClick: () -> Unit,
    onResetPasswordClick: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Login")

        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") }
        )

        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation()
        )

        if (errorMessage.isNotEmpty()) {
            Text(text = errorMessage)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            if (email.isEmpty() || password.isEmpty()) {
                errorMessage = "Please fill all fields"
            } else {
                onLogin()  // Appel de la fonction non-composable
            }
        }) {
            Text("Login")
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Don't have an account? Sign up",
            modifier = Modifier.clickable(onClick = onSignUpClick),
            textDecoration = TextDecoration.Underline
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Forgot your password? Reset here",
            modifier = Modifier.clickable(onClick = onResetPasswordClick),
            textDecoration = TextDecoration.Underline
        )
    }
}
