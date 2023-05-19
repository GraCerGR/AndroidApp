package com.example.myapplication.scripts

var ok = fun(): String { return "OK" }
var emptyInput = fun(): String { return "Empty Input" }
var memoryLimit = fun(): String { return "Memory limit" }
var negativeRoot = fun(): String { return "Root of a negative number" }
var zeroDivision = fun(): String { return "Division by zero" }
var incorrectExpression = fun(): String { return "Incorrect expression" }
var unexpectedSymbol = fun(operand: String): String { return "Unexpected symbol '$operand'" }
var undefinedVariable = fun(variableValue: String): String { return "Undefined variable '$variableValue'" }


