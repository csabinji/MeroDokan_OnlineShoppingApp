package com.sabin.onlineshoppingportal.api

import com.sabin.onlineshoppingportal.adapter.User
import com.sabin.onlineshoppingportal.entity.Cart
import com.sabin.onlineshoppingportal.response.*
import retrofit2.Response
import retrofit2.http.*

interface CartAPI {
    // Cart
    @POST("/cart/{id}")
    suspend fun addtoCart(
            @Header("Authorization") token: String,
            @Path("id") id:String,
            @Body cart : Cart
    ) : Response<CartResponse>

    //View Cart
    @GET("/acart")
    suspend fun getCart(
            @Header("Authorization") token : String
    ) : Response<ViewCartResponse>

    //Delete Product from Cart
    @DELETE("/cart/delete/{id}")
    suspend fun deleteCart(
            @Header("Authorization") token: String,
            @Path("id") id:String,
    ) : Response<DeleteCartResponse>
}