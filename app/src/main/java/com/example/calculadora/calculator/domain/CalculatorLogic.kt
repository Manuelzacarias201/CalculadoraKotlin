package com.example.calculadora.calculator.domain

class CalculatorLogic {
    fun calculate(n1: Double, n2: Double, operation: String): Double {
        return when (operation) { // el when es similar al switch
            "+" -> n1 + n2
            "-" -> n1 - n2
            "*" -> n1 * n2
            "/" -> if (n2 != 0.0) n1 / n2 else Double.NaN //indica un error matematico si se divide 0
            else -> n2
        }
    }
}