package com.hays.kotlinlearning.classes

import android.content.Context
import android.content.Intent
import android.util.Log
import java.util.*

/**
 * @author Hays
 * @date 25/12/2017
 */
class SomeClass {
    fun doSomething() = 1
}

class InjectableClass {
    private var value1: Int
    private var value2: Int = 0
    private var value3: Int = 0

    constructor() {
        value1 = 1
    }

    constructor(value1: Int, value2: Int): this() {
        this.value1 = value1
        this.value2 = value2
    }
    constructor(value1: Int, value2: Int, value3: Int): this(value1, value2) {
        this.value3 = value3
    }

    fun returnValue1() = value1
    fun returnValue2() = value2
    fun returnValue3() = value3
}

class Dependence {
    fun value() = 1
}

class InjectableClass2(private val dependence: Dependence) {
    fun plus1() = dependence.value() + 1
}

class MockClass {
    fun funcToBeMocked() = 1
    companion object {
        fun staticFunction() = 2
    }
}

class Collaborator {
    val mValue: Int
    val value: Int
        get() = mValue

    constructor() {
        mValue = -1
    }

    constructor(value: Int) {
        mValue = value
    }

    fun operation(a: Int, b: String, c: Date?): Boolean {
        return true
    }

    companion object {
        fun doSomething(b: Boolean, s: String) {
            throw IllegalStateException()
        }
    }
}

class VerifyExampleClass(val context: Context) {
    fun test1() {
        Log.e("error", "test 1 call")
    }

    fun test2() {
        Log.i("info", "test 2 call")
        context.startActivity(Intent())
    }

    fun test3() {
        context.sendBroadcast(Intent())
        Log.i("info", "test 3 call")
        context.startActivity(Intent())
    }

    fun test4() {
        UtilClass.doSomething1()
    }

    fun test5() {
        UtilClass.doSomething2()
    }
}

class UtilClass {
    companion object {
        fun doSomething1() = 1
        fun doSomething2() = 2
    }
}