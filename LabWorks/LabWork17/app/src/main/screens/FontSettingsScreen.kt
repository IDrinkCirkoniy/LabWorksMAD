package com.example.myapp.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun FontSettingsScreen() {
    var selectedColor by remember { mutableStateOf(Color.Black) }
    var fontSize by remember { mutableStateOf(16f) }
    var isBold by remember { mutableStateOf(false) }
    var isItalic by remember { mutableStateOf(false) }

    val colorOptions = listOf(
        Color.Black to "Черный",
        Color.Red to "Красный",
        Color.Blue to "Синий",
        Color.Green to "Зеленый",
        Color(0xFFFF9800) to "Оранжевый"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(text = "Настройки шрифта", fontSize = 24.sp)

        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Text(
                text = "Пример текста",
                fontSize = fontSize.sp,
                color = selectedColor,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(32.dp),
                style = if (isBold && isItalic) {
                    androidx.compose.ui.text.TextStyle(
                        fontSize = fontSize.sp,
                        fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                        fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
                    )
                } else if (isBold) {
                    androidx.compose.ui.text.TextStyle(fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                } else if (isItalic) {
                    androidx.compose.ui.text.TextStyle(fontStyle = androidx.compose.ui.text.font.FontStyle.Italic)
                } else {
                    androidx.compose.ui.text.TextStyle()
                }
            )
        }

        Text(text = "Цвет текста:", fontSize = 18.sp)

        colorOptions.forEach { (color, name) ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .selectable(
                        selected = selectedColor == color,
                        onClick = { selectedColor = color }
                    ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = selectedColor == color,
                    onClick = { selectedColor = color }
                )
                Text(text = name, modifier = Modifier.padding(start = 8.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Box(
                    modifier = Modifier
                        .size(24.dp)
                        .background(color)
                )
            }
        }

        Text(text = "Размер текста: ${fontSize.toInt()}px", fontSize = 18.sp)

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "10")
            Slider(
                value = fontSize,
                onValueChange = { fontSize = it },
                valueRange = 10f..30f,
                steps = 19,
                modifier = Modifier.weight(1f)
            )
            Text(text = "30")
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Полужирный:", fontSize = 16.sp)
            Switch(
                checked = isBold,
                onCheckedChange = { isBold = it }
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Курсив:", fontSize = 16.sp)
            Switch(
                checked = isItalic,
                onCheckedChange = { isItalic = it }
            )
        }
    }
}

@Composable
fun Box.background(color: Color): Modifier {
    return this.modifier.background(color)
}

fun Modifier.background(color: Color): Modifier {
    return this.then(androidx.compose.foundation.background(color))
}