package org.cmpai.project

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import org.cmpai.project.auth.AuthServiceImpl
import org.cmpai.project.auth.LoginScreen1
import org.cmpai.project.auth.LoginViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MaterialTheme {
        val loginViewModel = LoginViewModel(AuthServiceImpl(auth = Firebase.auth))

        Scaffold(
            modifier = Modifier.fillMaxWidth(),
        ) {
            LoginScreen1(onLoginSuccess = {}, viewModel = loginViewModel)
        }
    }
}