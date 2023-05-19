package com.example.myapplication.scripts

import java.util.EmptyStackException
import java.util.Stack
import kotlin.math.pow

fun arithmetics(mem: Memory, expression: String): Pair<String, Int> {
    val exp = expression.replace("\\s".toRegex(), "")
    if (exp.isEmpty()) {
        return Pair(emptyInput(), 0)
    }
    val (prepared, expStatus) = preparingExpression(mem, exp)
    val (correctLine, lineStatus) = lineCheck(exp)
    if (lineStatus == 0) {
        return Pair(correctLine, 0)
    }
    if (expStatus == 0) {
        return Pair(prepared, 0)
    }
    return rpnToAns(expressionToRPN(prepared))
}

private fun expressionToRPN(expression: String): String {
    var current = ""
    val stack: Stack<Char> = Stack<Char>()
    var priority: Int
    for (i in expression.indices) {
        priority = getPriority(expression[i])
        when (priority) {
            0 -> current += expression[i]
            1 -> stack.push(expression[i])
            2, 3, 4 -> {
                current += " "
                while (!stack.empty()) {
                    if ((getPriority(stack.peek()) >= priority)) current += stack.pop()
                    else break
                }
                stack.push(expression[i])
            }
            -1 -> {
                current += " "
                while (getPriority(stack.peek()) != 1) current += stack.pop()
                stack.pop()
            }
            else -> {
                return "Error"
            }
        }
    }
    while (!stack.empty()) current += stack.pop()
    return current
}

private fun rpnToAns(rpn: String): Pair<String, Int> {
    var operand = String()
    val stack: Stack<Int> = Stack<Int>()
    var i = 0
    while (i < rpn.length) {
        if (rpn[i] == ' ') {
            i++
            continue
        }
        if (getPriority(rpn[i]) == 0) {
            while (rpn[i] != ' ' && (getPriority(rpn[i]) == 0)) {
                operand += rpn[i++]
                if (i == rpn.length) break
            }
            try {
                stack.push(operand.toInt())
            } catch (e: NumberFormatException) {
                return Pair(unexpectedSymbol(operand), 0)
            }
            operand = String()
        }
        if (i == rpn.length) break
        if (getPriority(rpn[i]) > 1) {
            try {
                val a: Int = stack.pop()
                val b: Int = stack.pop()
                when (rpn[i]) {
                    '^' -> {
                        val result = b.toDouble().pow(a).toLong()
                        if (result >= 2147483647) {
                            return Pair(memoryLimit(), 0)
                        }
                        stack.push(result.toInt())
                    }
                    '#' -> {
                        val step = 1 / a.toDouble()
                        val result = b.toDouble().pow(step).toLong()

                        if (b < 0) {
                            return Pair(negativeRoot(), 0)
                        }
                        if (result >= 2147483647) {
                            return Pair(memoryLimit(), 0)
                        }
                        stack.push(result.toInt())
                    }
                    '+' -> stack.push(b + a)
                    '-' -> stack.push(b - a)
                    '*' -> stack.push(b * a)
                    '/' -> {
                        try {
                            stack.push(b / a)
                        } catch (e: Exception) {
                            return Pair(zeroDivision(), 0)
                        }
                    }
                    '%' -> {
                        try {
                            stack.push(b % a)
                        } catch (e: Exception) {
                            return Pair(zeroDivision(), 0)
                        }
                    }
                    else -> {
                        return Pair(unexpectedSymbol(rpn[i].toString()), 0)
                    }
                }
            } catch (e: EmptyStackException) {
                return Pair(incorrectExpression(), 0)
            }
        }
        i++
    }
    return Pair(ok(), stack.pop())
}

private fun getPriority(token: Char): Int {
    return when (token) {
        '#', '^' -> 4
        '*', '/', '%' -> 3
        '+', '-' -> 2
        '(' -> 1
        ')' -> -1
        else -> 0
    }
}


private fun lineCheck(string: String): Pair<String, Int> {
    var str = string.replace("[A-Za-z-+*/0-9()%_^#\\[\\]]".toRegex(), "")
    if (str.isNotEmpty()) {
        return Pair(unexpectedSymbol(str), 0)
    }
    val reg =
        "([-+%#^ ]+[0-9_]+[A-Za-z_]+[0-9]*[-+%*#^ ]*)|(\\b[0-9_]+[A-Za-z_]+[0-9]*)|(\\b[_][0-9]+)".toRegex()
    if (reg.find(string) != null) {
        return Pair(incorrectExpression(), 0)
    }
    str = string.replace("[A-Za-z-+*/0-9%^#_\\[\\]]".toRegex(), "")
    val bracket1 = str.replace("\\(".toRegex(), "")
    val bracket2 = str.replace("\\)".toRegex(), "")
    if (bracket1.length != bracket2.length) {
        return Pair(incorrectExpression(), 0)
    }
    return Pair(ok(), 1)
}

private fun preparingExpression(mem: Memory, expression: String): Pair<String, Int> {
    var exp = expression
    var preparedExpression = String()

    val reg = "[A-Za-z]+[A-Za-z0-9_]*".toRegex()
    var variable = reg.find(exp)
    while (variable != null) {
        if (!mem.isVariableExist(variable.value)) {
            return Pair(undefinedVariable(variable.value), 0)
        }
        val varValue = mem.getVariableValue(variable.value)
        var fromVarToNum = varValue.toString()
        if (varValue!!.toInt() < 0) {
            fromVarToNum = "($fromVarToNum)"
        }
        exp = exp.replaceRange(variable.range, fromVarToNum)
        variable = reg.find(exp)
    }

    for (j in exp.indices) {
        if (exp[j] == '-') {
            if ((j == 0) || (exp[j - 1] == '(')) {
                preparedExpression += "0"
            }
        }
        preparedExpression += exp[j]
    }
    return Pair(preparedExpression, 1)
}
