package com.example.practicacalculadora.models

class Calculator {
    private var result: String = ""
    private var prevNumber: Int = 0
    private var currentOperation: OperationType = OperationType.NONE
    private var memory: Int = 0

    fun clearEverything() {
        resetCalculator()
    }

    private fun resetCalculator() {
        prevNumber = 0
        currentOperation = OperationType.NONE
        result = ""
    }

    fun clearOne(): String {
        return if (result.isEmpty()) {
            ""
        } else {
            result = result.dropLast(1)
            result
        }
    }

    @Throws(ArithmeticException::class)
    fun solveOperation(): String {
        val currentNumber = result.toInt()
        result = calculateResult(currentNumber).toString()
        return result
    }

    private fun calculateResult(currentNumber: Int): Int {
        return when (currentOperation) {
            OperationType.ADDITION -> prevNumber + currentNumber
            OperationType.SUBTRACTION -> prevNumber - currentNumber
            OperationType.MULTIPLICATION -> prevNumber * currentNumber
            OperationType.DIVISION -> divideNumbers(currentNumber)
            OperationType.NONE -> currentNumber
        }
    }

    private fun divideNumbers(currentNumber: Int): Int {
        if (currentNumber == 0) {
            throw ArithmeticException("Division by 0")
        }
        return prevNumber / currentNumber
    }

    fun startOperation(operationType: OperationType) {
        setOperation(operationType)
    }

    private fun setOperation(operationType: OperationType) {
        currentOperation = operationType
        prevNumber = result.toInt()
        result = ""
    }

    fun addNumber(number: Int): String {
        appendNumber(number)
        return result
    }

    private fun appendNumber(number: Int) {
        result += number
        result = result.toInt().toString()
    }

    fun memoryClear() {
        memory = 0
    }

    fun memoryRecall(): Int {
        return memory
    }

    fun memoryAdd() {
        memory += result.toInt()
    }

    fun memorySubtract() {
        memory -= result.toInt()
    }
}
