package com.example.healthlog

import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals("1:0:1",makeTimeFormat(361))
        assertEquals("1:1:1",makeTimeFormat(421))

    }

    private fun makeTimeFormat(time: Long) : String{
        val hour = time/360
        val minute = (time%360)/60
        val second = (time%360)%60

        return "${hour}:${minute}:${second}"
    }
}
