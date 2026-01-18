package com.example.calculadora.calculator.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Backspace
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.calculadora.calculator.presentation.viewmodels.CalculatorViewModel

@Composable
fun CalculatorScreen(
    modifier: Modifier = Modifier,
    viewModel: CalculatorViewModel = viewModel()
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(bottom = 24.dp, start = 16.dp, end = 16.dp),
        verticalArrangement = Arrangement.Bottom
    ) {
        // Pantalla de resultado
        Text(
            text = viewModel.display,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp, end = 12.dp),
            color = Color.Black,
            fontSize = 80.sp,
            fontWeight = FontWeight.Light,
            textAlign = TextAlign.End,
            maxLines = 1
        )

        // Rejilla de botones
        val buttons = listOf(
            listOf("⌫", "AC", "%", "/"),
            listOf("7", "8", "9", "*"),
            listOf("4", "5", "6", "-"),
            listOf("1", "2", "3", "+"),
            listOf("+/-", "0", ".", "=")
        )

        buttons.forEach { row ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                row.forEach { symbol ->
                    val color = when (symbol) {
                        "/", "*", "-", "+", "=" -> Color(0xFFFF9F0A) // Naranja
                        "⌫", "AC", "%", "+/-" -> Color(0xFFA5A5A5) // Gris claro
                        else -> Color(0xFF333333) // Gris oscuro para números
                    }
                    val textColor = if (symbol in listOf("⌫", "AC", "%", "+/-")) Color.Black else Color.White
                    
                    CalcButton(
                        symbol = symbol,
                        containerColor = color,
                        contentColor = textColor,
                        modifier = Modifier.weight(1f)
                    ) {
                        when (symbol) {
                            "AC" -> viewModel.onClear()
                            "⌫" -> viewModel.onDelete()
                            "%" -> viewModel.onPercent()
                            "+/-" -> viewModel.onToggleSign()
                            "=" -> viewModel.onCalculate()
                            "/" -> viewModel.onOperation("/")
                            "*" -> viewModel.onOperation("*")
                            "-" -> viewModel.onOperation("-")
                            "+" -> viewModel.onOperation("+")
                            else -> viewModel.onDigit(symbol)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CalcButton(
    symbol: String,
    containerColor: Color,
    contentColor: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = modifier.aspectRatio(1f),
        shape = CircleShape,
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = contentColor
        ),
        contentPadding = PaddingValues(0.dp)
    ) {
        if (symbol == "⌫") {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.Backspace,
                contentDescription = "Borrar",
                modifier = Modifier.size(28.dp)
            )
        } else {
            val displaySymbol = when(symbol) {
                "/" -> "÷"
                "*" -> "×"
                "-" -> "−"
                else -> symbol
            }
            Text(
                text = displaySymbol,
                fontSize = if (symbol.length > 2) 20.sp else 30.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CalculatorScreenPreview() {
    CalculatorScreen()
}
