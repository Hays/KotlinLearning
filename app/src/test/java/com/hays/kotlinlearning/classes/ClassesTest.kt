package com.hays.kotlinlearning.classes

import android.content.Context
import android.content.Intent
import android.util.Log
import junit.framework.Assert
import mockit.*
import mockit.integration.junit4.JMockit
import org.junit.Test
import org.junit.runner.RunWith
import java.util.*

/**
 * @author Hays
 * @date 25/12/2017
 */
@RunWith(JMockit::class)
class ClassesTest {
    @Test
    fun testDoSomthine(@Mocked someClass: SomeClass) {
        Assert.assertEquals(0, someClass.doSomething())
    }

    @Test
    fun testInjectable(@Tested obj: InjectableClass) {
        Assert.assertEquals(1, obj.returnValue1())
    }

    @Test
    fun testInjectable(@Tested obj: InjectableClass, @Injectable(value = "102") value2: Int, @Injectable("101") value1: Int) {
        Assert.assertEquals(101, obj.returnValue1())
        Assert.assertEquals(102, obj.returnValue2())
    }

    @Test
    fun testInjectable(@Tested obj: InjectableClass, @Injectable(value = "102") value2: Int, @Injectable("101") value1: Int, @Injectable("103") value3: Int) {
        Assert.assertEquals(101, obj.returnValue1())
        Assert.assertEquals(102, obj.returnValue2())
        Assert.assertEquals(103, obj.returnValue3())
    }

    @Test
    fun testInjectable(@Tested obj: InjectableClass, @Injectable(value = "102") value2: Int, @Injectable("101") value1: Int, @Injectable("103") value3: Int, @Injectable("104") value4: Int) {
        Assert.assertEquals(101, obj.returnValue1())
        Assert.assertEquals(102, obj.returnValue2())
        Assert.assertEquals(103, obj.returnValue3())
    }

    @Test
    fun testInjectable2(@Tested obj: InjectableClass2, @Injectable dependence: Dependence) {
        Assert.assertEquals(0, dependence.value())
        Assert.assertEquals(1, Dependence().value())
        object : Expectations() {
            init {
                dependence.value()
                result = 99
            }
        }
        Assert.assertEquals(100, obj.plus1())
    }

    @Test
    fun testFakeDependence() {
        class FakeDependence : MockUp<Dependence>() {
            @Mock
            fun value() = 2
        }
        FakeDependence()

        Assert.assertEquals(2, Dependence().value())
    }

    @Test
    fun testMockClass(@Mocked obj: MockClass, @Mocked companion: MockClass.Companion) {
        object : Expectations() {
            init {
                obj.funcToBeMocked()
                result = 101
                times = 1
                companion.staticFunction()
                result = 102
                times = 2
            }
        }

        Assert.assertEquals(101, obj.funcToBeMocked())
        Assert.assertEquals(102, MockClass.staticFunction())
        Assert.assertEquals(102, companion.staticFunction())
    }

    @Test
    fun testMockingClass() {
        val anyInstance = Collaborator()
        object : Expectations(Collaborator::class.java) {
            init {
                // Notice: 这里如果构造函数放在里面创建，例如：Collaborator().value这样，会对无参数的构造函数也进行了mock，导致原来的构造函数无效
                anyInstance.value
                result = 100
            }
        }

        val c1 = Collaborator()
        val c2 = Collaborator(10)

        Assert.assertEquals(100, c1.value)
        Assert.assertEquals(100, c2.value)

        Assert.assertTrue(c1.operation(1, "2", null))
        Assert.assertEquals(10, c2.mValue)
    }

    @Test
    fun testMockingInstance() {
        val anyInstance = Collaborator()
        object : Expectations(anyInstance) {
            init {
                anyInstance.value
                result = 100
                anyInstance.operation(anyInt, anyString, any as Date?)
                result = false
            }
        }

        val c1 = Collaborator()
        val c2 = Collaborator(10)

        Assert.assertEquals(-1, c1.value)
        Assert.assertEquals(10, c2.value)
        Assert.assertEquals(100, anyInstance.value)
        Assert.assertFalse(anyInstance.operation(2, "2", null))

        object : Expectations(Collaborator.Companion) {
            init {
                Collaborator.doSomething(anyBoolean, anyString)
            }
        }

        Collaborator.doSomething(true, "test")
    }

    @Injectable
    private lateinit var anyContext: Context
    @Mocked
    private lateinit var anyLog: Log
    @Tested
    private lateinit var testInstance: VerifyExampleClass

    @Test
    fun testVerification1() {
        testInstance.test1()
        object : Verifications() {
            init {
                Log.e("error", anyString)
                times = 1
            }
        }
    }

    @Test
    fun testVerification2() {
        testInstance.test2()
        object : VerificationsInOrder() {
            init {
                Log.i("info", "test 2 call")
                times = 1
                anyContext.startActivity(any as Intent?)
                times = 1
            }
        }
    }

    @Test
    fun testVerification4() {
        object : Expectations(UtilClass.Companion) {
            init {
                UtilClass.doSomething1()
            }
        }
        testInstance.test4()
        object : Verifications() {
            init {
                UtilClass.doSomething1()
                times = 1
            }
        }
    }

    @Test
    fun testVerification5() {
        testInstance.test5()
        object : Verifications() {
            init {
                UtilClass.doSomething2()
                times = 1
            }
        }
    }
}