package com.sabin.onlineshoppingportal.api

import com.sabin.onlineshoppingportal.entity.Cart
import com.sabin.onlineshoppingportal.entity.Order
import com.sabin.onlineshoppingportal.response.*
import retrofit2.Response
import retrofit2.http.*

interface OrderAPI {
    // Order
    @POST("/order/{id}")
    suspend fun addtoCart(
            @Header("Authorization") token: String,
            @Path("id") id:String,
            @Body order : Order
    ) : Response<OrderResponse>

    //View Order for buyer
    @GET("/aorder")
    suspend fun getOrder(
        @Header("Authorization") token : String
    ) : Response<ViewOrderResponse>

    //View Order for Seller
    @GET("/asorder")
    suspend fun getSOrder(
            @Header("Authorization") token : String
    ) : Response<ViewOrderResponse>

    //Delete Product from Order
    @DELETE("/order/delete/{id}")
    suspend fun deleteOrder(
        @Header("Authorization") token: String,
        @Path("id") id:String,
    ) : Response<DeleteOrderResponse>
}