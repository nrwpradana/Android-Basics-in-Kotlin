package com.example.cupcake

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.cupcake.model.OrderViewModel
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class ViewModelTests {
    // This is a JUnit test rule that swaps the background executor used
    // by the architecture components with a different one that executes each task synchronously.
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    /**
     * In this test, will be checking to make sure the quantity object in the OrderViewModel
     * is updated when setQuantity is called.
     * */
    @Test
    fun quantity_twelve_cupcakes() {
        val orderViewModel = OrderViewModel()
        // when testing the values of a LiveData object, the objects need to be observed
        // in order for changes to be emitted.
        orderViewModel.quantity.observeForever {}
        // Update the quantity object
        orderViewModel.setQuantity(12)
        assertEquals(12, orderViewModel.quantity.value)
    }

    /**
     * Test the price of twelve cupcakes when the quantity is selected
     */
    @Test
    fun price_twelve_cupcakes() {
        // One of the main functions of the OrderViewModel is to calculate the price of our order.
        // This happens when we select a quantity of cupcakes, and when we select a pick up date.
        // The price calculation happens in a private method, so our test cannot call this method directly.
        // Only other methods in the OrderViewModel can call it.
        // Those methods are public, so we'll call those in order to trigger the price calculation
        // so we can check that the value of the price is what we expect.
        val orderViewModel = OrderViewModel()

        // The value of the price is set by using a Transformation. transforms it to a currency format
        // When transforming a LiveData object, the code doesn't get called unless it absolutely
        // has to be, this saves resources on a mobile device.
        // The code will only be called if we observe the object for changes.
        // Of course, this is done in our app, but we also need to do the same for the test.
        orderViewModel.price.observeForever {}
        // Select the quantity of cupcakes to calculate the price
        orderViewModel.setQuantity(12)
        // cupcakes are $2.00 each. resetOrder() is called every time the ViewModel is initialized,
        // and in this method, the default date is today's date, and PRICE_FOR_SAME_DAY_PICKUP is $3.00.
        // Therefore, 12 * 2 + 3 = 27.
        // We expect that the value of the price variable, after selecting 12 cupcakes, to be $27.00.
        assertEquals("$27.00", orderViewModel.price.value)
    }
}