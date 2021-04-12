package com.sabin.onlineshoppingportal.repository

import com.sabin.onlineshoppingportal.api.MyApiRequest
import com.sabin.onlineshoppingportal.api.OrderAPI
import com.sabin.onlineshoppingportal.api.ServiceBuilder
import com.sabin.onlineshoppingportal.entity.Order
import com.sabin.onlineshoppingportal.response.OrderResponse

class OrderRepository :
MyApiRequest() {
    private val OrderAPI =
            ServiceBuilder.buildService(OrderAPI::class.java)

    // Add to Order

    suspend fun addtoOrder(id : String, order : Order) : OrderResponse{
        return apiRequest {
            OrderAPI.addtoCart(ServiceBuilder.token!!,id,order)
        }
    }
}