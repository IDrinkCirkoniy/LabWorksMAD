package com.example.myapp.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PinCodeScreen() {
    val correctPin = "2283"
    var enteredPin by remember { mutableStateOf("") }
    var attemptsLeft by remember { mutableStateOf(3) }
    var isBlocked by remember { mutableStateOf(false) }
    var message by remember { mutableStateOf("") }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Введите пин-код", fontSize = 24.sp)

        Spacer(modifier = Modifier.height(32.dp))

        OutlinedTextField(
            value = enteredPin,
            onValueChange = { newValue ->
                if (newValue.length <= 4 && !isBlocked) {
                    enteredPin = newValue
                }
            },
            label = { Text("Пин-код") },
            placeholder = { Text("Введите 4 цифры") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
            isEnabled = !isBlocked,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Осталось попыток: $attemptsLeft", color = MaterialTheme.colorScheme.error)

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                when {
                    enteredPin == correctPin -> {
                        message = "Код верный!"
                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                    }
                    attemptsLeft > 1 -> {
                        attemptsLeft--
                        message = "Неверный код. Осталось попыток: $attemptsLeft"
                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                        enteredPin = ""
                    }
                    else -> {
                        attemptsLeft = 0
                        isBlocked = true
                        message = "Попытки закончились. Доступ заблокирован."
                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                    }
                }
            },
            enabled = !isBlocked && enteredPin.length == 4,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Отправить")
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (message.isNotEmpty()) {
            Text(text = message)
        }
    }
}