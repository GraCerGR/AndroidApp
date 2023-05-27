package com.example.myapplication.scripts

var ok = fun(): String { return "OK" }
var emptyInput = fun(): String { return "Empty Input" }
var memoryLimit = fun(): String { return "Memory limit" }
var negativeRoot = fun(): String { return "Root of a negative number" }
var zeroDivision = fun(): String { return "Division by zero" }
var incorrectExpression = fun(): String { return "Invalid expression" }
var invalidComparator = fun(): String { return "Invalid comparator" }
var inputError = fun(): String { return "Input error" }
var variableAssignSequence = fun(): String { return "Can't assign this to variable" }
var unexpectedSymbol = fun(operand: String): String { return "Unexpected symbol '$operand'" }
var undefinedVariable =
    fun(variableValue: String): String { return "This variable is undefined '$variableValue'" }
var incorrectNaming = fun(name: String): String { return "Incorrect naming '$name'" }
var tagNaN = fun(): String { return "NaN" }
var tagVariable = fun(): String { return "Variable" }
var tagArray = fun(): String { return "Array" }
var programFinish = fun(status: String): String { return "Program finished with status: $status" }
var programStart = fun(): String { return "Started successfully" }
var allComparators = listOf(">", ">=", "<", "<=", "==", "!=")
var titleInput = "Input a value"
