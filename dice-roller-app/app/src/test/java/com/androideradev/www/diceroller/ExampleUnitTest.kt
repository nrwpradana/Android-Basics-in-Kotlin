package com.androideradev.www.diceroller

import org.junit.Assert.assertTrue
import org.junit.Test

class ExampleUnitTest {

    @Test
    fun generates_number() {
        val dice = Dice()
        val rollResult = dice.roll()
        assertTrue(
            "The value of rollResult was not between 1 and 6",
            rollResult in 1..6
        )
    }
}
