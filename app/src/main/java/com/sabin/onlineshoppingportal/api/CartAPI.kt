package com.sabin.onlineshoppingportal.api

import com.sabin.onlineshoppingportal.adapter.User
import com.sabin.onlineshoppingportal.entity.Cart
import com.sabin.onlineshoppingportal.response.CartResponse
import com.sabin.onlineshoppingportal.response.SignupResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface CartAPI {
    // Cart
    @POST("/cart/{id}")
    suspend fun addtoCart(
            @Header("Authorization") token: String,
            @Path("id") id:String,
            @Body cart : Cart
    ) : Response<CartResponse>
}