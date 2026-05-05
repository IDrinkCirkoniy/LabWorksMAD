package com.example.myapp.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutMeScreen() {
    var text by remember { mutableStateOf("") }
    val maxLength = 300
    val remainingChars = maxLength - text.length

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(text = "О себе", fontSize = 24.sp)

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = text,
            onValueChange = { newValue ->
                if (newValue.length <= maxLength) {
                    text = newValue
                }
            },
            label = { Text("Расскажите о себе") },
            placeholder = { Text("Ваши увлечения, опыт, цели...") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            minLines = 5,
            maxLines = 10
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Осталось символов: $remainingChars",
            fontSize = 12.sp,
            color = if (remainingChars < 50) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary
        )
    }
}