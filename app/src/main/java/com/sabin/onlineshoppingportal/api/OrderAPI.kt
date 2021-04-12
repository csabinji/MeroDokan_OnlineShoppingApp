package com.sabin.onlineshoppingportal.api

import com.sabin.onlineshoppingportal.entity.Cart
import com.sabin.onlineshoppingportal.entity.Order
import com.sabin.onlineshoppingportal.response.CartResponse
import com.sabin.onlineshoppingportal.response.OrderResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface OrderAPI {
    // Order
    @POST("/order/{id}")
    suspend fun addtoCart(
            @Header("Authorization") token: String,
            @Path("id") id:String,
            @Body order : Order
    ) : Response<OrderResponse>

}