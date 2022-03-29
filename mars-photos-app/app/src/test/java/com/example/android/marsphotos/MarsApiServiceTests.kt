package com.example.android.marsphotos

import BaseTest
import com.example.android.marsphotos.network.MarsApiService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


class MarsApiServiceTests : BaseTest() {

    private lateinit var service: MarsApiService

    @Before
    fun setup() {
        // mockWebServer, which is an instance of the MockWebServer object.
        // This object will intercept our network requests
        //  but we first need to direct our network requests to the URL that will be intercepted.

        // url() that specifies which URL we want to intercept.
        // we don't want to make a real network request,
        // we only want to pretend to make one so that we can test the network code with data that we control in the test itself.

        // The url() function takes a string that represents that fake URL and it returns an HttpUrl object.
        val url = mockWebServer.url("/")
        //  an instance of the MarsApiService in the same way that was done in the MarsApiService
        //  and MarsApiClass (excluding the lazy variable).
        service = Retrofit.Builder()
            // This specifies to our API service that we want to route requests to our MockWebServer.
            .baseUrl(url)
            .addConverterFactory(
                MoshiConverterFactory.create(
                    Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
                )
            )
            .build()
            .create(MarsApiService::class.java)
    }

    @Test
    fun api_service() {
        // You can think of MockWebServer as a fake API that returns data that we created, and as such,
        // we need to explicitly tell MockWebServer what to return before a request is made.
        // This is where the enqueue() function in BaseTest comes in.
        enqueue("mars_photos.json")
        // getPhotos() is a suspend function, and it must be called from a coroutine scope.
        runBlocking {
            val apiResponse = service.getPhotos()
            // make sure that our getPhotos() response is not null.
            assertNotNull(apiResponse)
            // make sure that the list is not empty.
            assertTrue("The list wast empty", apiResponse.isNotEmpty())
            // An assertion to check that some of the data is correct
            // Assert that the value of that ID of first item equals the value of the ID from the corresponding list item.
            assertEquals("The IDs did not match", "424905", apiResponse[0].id)
            // Assert the size of our data equals to the value of the corresponding response list size
            assertEquals("The size of items is not match", 2, apiResponse.size)
        }
    }
}