package com.example.calculadora.calculator.presentation.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.calculadora.calculator.domain.CalculatorLogic

class CalculatorViewModel : ViewModel() {
    var display by mutableStateOf("0")
    private var firstOperand by mutableStateOf<Double?>(null)
    private var pendingOperation by mutableStateOf<String?>(null)
    private var shouldResetDisplay by mutableStateOf(false)

    private val logic = CalculatorLogic()

    fun onDigit(digit: String) {
        if (display == "0" || shouldResetDisplay) {
            display = digit
            shouldResetDisplay = false
        } else {
            display += digit
        }
    }

    fun onOperation(op: String) {
        val currentVal = display.toDoubleOrNull() ?: 0.0
        if (firstOperand == null) {
            firstOperand = currentVal
        } else if (pendingOperation != null) {
            val result = logic.calculate(firstOperand!!, currentVal, pendingOperation!!)
            display = formatResult(result)
            firstOperand = result
        }
        pendingOperation = op
        shouldResetDisplay = true
    }

    fun onCalculate() {
        val currentVal = display.toDoubleOrNull() ?: 0.0
        if (firstOperand != null && pendingOperation != null) {
            val result = logic.calculate(firstOperand!!, currentVal, pendingOperation!!)
            display = formatResult(result)
            firstOperand = null
            pendingOperation = null
            shouldResetDisplay = true
        }
    }

    fun onClear() {
        display = "0"
        firstOperand = null
        pendingOperation = null
        shouldResetDisplay = false
    }

    fun onDelete() {
        if (display.length > 1) {
            display = display.dropLast(1)
        } else {
            display = "0"
        }
    }

    fun onPercent() {
        val currentVal = display.toDoubleOrNull() ?: 0.0
        display = formatResult(currentVal / 100)
    }

    fun onToggleSign() {
        if (display != "0") {
            display = if (display.startsWith("-")) display.drop(1) else "-$display"
        }
    }

    private fun formatResult(result: Double): String {
        return if (result.isNaN()) "Error"
        else if (result == result.toLong().toDouble()) result.toLong().toString()
        else result.toString()
    }
}