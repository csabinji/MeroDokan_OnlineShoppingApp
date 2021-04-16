package com.sabin.ospwearos.api

import com.sabin.ospwearos.response.ViewOrderResponse
import retrofit2.Response
import retrofit2.http.*

interface OrderAPI {

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
}