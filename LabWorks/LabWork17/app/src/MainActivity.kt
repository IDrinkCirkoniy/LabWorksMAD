package com.example.myapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapp.screens.*
import com.example.myapp.ui.theme.MyAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavigation()
                }
            }
        }
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = false,
                    onClick = { navController.navigate("registration") },
                    label = { Text("Регистрация") }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = { navController.navigate("pin") },
                    label = { Text("Пин-код") }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = { navController.navigate("about") },
                    label = { Text("О себе") }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = { navController.navigate("settings") },
                    label = { Text("Настройки") }
                )
            }
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = "registration",
            modifier = Modifier.padding(paddingValues)
        ) {
            composable("registration") { RegistrationScreen() }
            composable("pin") { PinCodeScreen() }
            composable("about") { AboutMeScreen() }
            composable("settings") { FontSettingsScreen() }
        }
    }
}