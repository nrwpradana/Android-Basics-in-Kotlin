package com.example.android.marsphotos.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import retrofit2.http.GET

private const val BASE_URL = "https://android-kotlin-fun-mars-server.appspot.com"

/**
 * Build the Moshi object with Kotlin adapter factory that Retrofit will be using.
 */
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

/**
 * Retrofit needs the base URI for the web service, and a converter factory to build a web services API.
 * */
private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

/**
 * an interface that defines how Retrofit talks to the web server using HTTP requests.
 */
interface MarsApiService {
    /**
     * @GET annotation to tell Retrofit that this is GET request,
     * and specify endpoint, for that web service method. In this case the endpoint is called photos.
     * */
    @GET("photos")
    suspend fun getPhotos(): List<MarsPhoto>
}

/**
 * A public Api object that exposes the lazy-initialized Retrofit service
 */
object MarsApi {
    val service: MarsApiService by lazy { retrofit.create(MarsApiService::class.java) }
}
