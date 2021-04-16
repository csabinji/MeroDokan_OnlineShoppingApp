package com.sabin.onlineshoppingportal.api

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
    // Load Image
    fun loadImagePath(): String {
        val arr = BASE_URL.split("/").toTypedArray()
        return arr[0] + "/" + arr[1] + arr[2] + "/uploads/"
    }
}