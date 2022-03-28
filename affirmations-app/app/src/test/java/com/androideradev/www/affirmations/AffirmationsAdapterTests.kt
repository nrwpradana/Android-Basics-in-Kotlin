package com.androideradev.www.affirmations

import android.content.Context
import com.androideradev.www.affirmations.adapter.AffirmationItemAdapter
import com.androideradev.www.affirmations.model.Affirmation
import org.junit.Test

import org.junit.Assert.*
import org.mockito.Mockito.mock

class AffirmationsAdapterTests {

    private val context = mock(Context::class.java)

    @Test
    fun adapter_size() {

        val data = listOf(
            Affirmation("I am strong.", R.drawable.image1),
            Affirmation("I believe in myself.", R.drawable.image2)
        )

        val adapter = AffirmationItemAdapter(data)
        assertEquals(
            //Displays in the test result if the test fails
            "AffirmationAdapter is not the correct size",
            data.size, // the expected value
            adapter.itemCount // the actual value
        )
    }
}