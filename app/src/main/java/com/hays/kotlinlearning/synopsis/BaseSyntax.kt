package com.hays.kotlinlearning.synopsis

/**
 * @author Hays
 * @date 23/12/2017
 */
fun sum(a: Int, b: Int): Int {
    return a + b
}

fun sum2(a: Int, b: Int) = a + b

var x = 0
fun autoIncrease() {
    x++
}

fun checkString(obj: Any): Boolean {
    return (obj is String)
}

fun checkIsNotString(obj: Any): Boolean {
    return (obj !is String)
}

fun useRange(maxSize: Int): Int {
    var size = maxSize
    if (maxSize !in 1..10) {
        size = 10
    }
    return (1..size).sumBy { it *10 }
}