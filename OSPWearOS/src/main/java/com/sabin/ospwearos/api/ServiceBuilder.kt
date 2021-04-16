package com.sabin.ospwearos.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceBuilder {
    private const val BASE_URL =
        "http://10.0.2.2:90/"



//    private const val BASE_URL = "http://localhost:90/"


    var token: String? = null
    var accountType: String? = null

    private val okHttp = OkHttpClient.Builder()
    private val retrofitBuilder = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttp.build())

    // Retrofit Instance
    private val retrofit = retrofitBuilder.build()

    //Generic function
    fun <T> buildService(serviceTypes: Class<T>): T {
        return retrofit.create(serviceTypes)
    }
}