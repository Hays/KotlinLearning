package com.hays.kotlinlearning.synopsis

import mockit.integration.junit4.JMockit
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

/**
 * @author Hays
 * @date 23/12/2017
 */
@RunWith(JMockit::class)
class BaseSyntaxTest {

    @Test
    fun testSumFunc() {
        var ret = sum(1, 2)
        assertEquals(3, ret)
        ret = sum2(1, 2 )
        assertEquals(3, ret)
    }

    @Test
    fun testIncrease() {
        for (i in 1..3) {
            autoIncrease()
        }
        assertEquals(3, x)
    }

    @Test
    fun testCheckString() {
        assertEquals(true, checkString("123"))
        assertEquals(false, checkString(123))
    }

    @Test
    fun testCheckNotString() {
        assertEquals(true, checkIsNotString(123))
        assertEquals(false, checkIsNotString("123"))
    }
}