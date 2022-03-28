package com.example.cupcake.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

const val PRICE_PER_CUPCAKE = 2.00
private const val PRICE_FOR_SAME_DAY_PICKUP = 3.00

class OrderViewModel : ViewModel() {

    private val _quantity: MutableLiveData<Int> = MutableLiveData()
    val quantity: LiveData<Int> get() = _quantity

    private val _flavor: MutableLiveData<String> = MutableLiveData()
    val flavor: LiveData<String> get() = _flavor

    private val _date: MutableLiveData<String> = MutableLiveData()
    val date: LiveData<String> get() = _date

    private val _price: MutableLiveData<Double> = MutableLiveData()
    val price: LiveData<String> = Transformations.map(_price) {
        // getCurrencyInstance() method in the NumberFormat class is used to convert
        // the price to local currency format.
        NumberFormat.getCurrencyInstance().format(it)
    }

    val dateOptions get() = getPickupOptions()

    init {
        // Initialize the properties when an instance of OrderViewModel is created.
        resetOrder()
    }

    fun setQuantity(numberCupcakes: Int) {
        _quantity.value = numberCupcakes
        // Update the price variable when the quantity is set.
        updatePrice()
    }

    fun setFlavor(desiredFlavor: String) {
        _flavor.value = desiredFlavor
    }

    fun setData(date: String) {
        _date.value = date
        // Add the same day pickup charges if the user select the current day for pickup
        updatePrice()
    }

    /**
     * check if the flavor for the order has been set or not.
     */
    fun hasNoFlavorSet() = _flavor.value.isNullOrEmpty()

    /**
     * Build up a list of dates starting with the current date and the following three dates.
     * */
    fun getPickupOptions(): List<String> {
        val options = mutableListOf<String>()
        val formatter = SimpleDateFormat("E MMM d", Locale.getDefault())
        // This variable will contain the current date and time.
        val calendar = Calendar.getInstance()

        //  Because you'll need 4 date options, repeat this block of code 4 times.
        repeat(4) {
            // Format a date, add it to the list of date options
            options.add(formatter.format(calendar.time))
            //  Increment the calendar by 1 day.
            calendar.add(Calendar.DATE, 1)
        }
        return options
    }

    /**
     *  Reset the MutableLiveData properties in the view model. Assign the current date value
     *  from the dateOptions list to _date.value.
     * */
    fun resetOrder() {
        _quantity.value = 0
        _flavor.value = ""
        _date.value = dateOptions[0]
        _price.value = 0.0
    }

    /**
     *  A helper method to calculate the price.
     * */
    private fun updatePrice() {
        var calculatedPrice = (quantity.value ?: 0) * PRICE_PER_CUPCAKE
        // Check if the user selected the same day pickup.
        // Check if the date in the view model (_date.value) is the same
        // as the first item in the dateOptions list which is always the current day.
        if (dateOptions[0] == _date.value) {
            calculatedPrice += PRICE_FOR_SAME_DAY_PICKUP
        }
        _price.value = calculatedPrice
    }

}