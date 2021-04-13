package com.sabin.onlineshoppingportal.repository

import com.sabin.onlineshoppingportal.api.MyApiRequest
import com.sabin.onlineshoppingportal.api.OrderAPI
import com.sabin.onlineshoppingportal.api.ServiceBuilder
import com.sabin.onlineshoppingportal.entity.Order
import com.sabin.onlineshoppingportal.response.*

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

    // Order List

    suspend fun getOrder() : ViewOrderResponse {
        return apiRequest {
            OrderAPI.getOrder(
                ServiceBuilder.token!!
            )
        }
    }

    // Delete Order

    suspend fun deleteOrder(order : String) : DeleteOrderResponse {
        return apiRequest {
            OrderAPI.deleteOrder(
                ServiceBuilder.token!!, order
            )
        }
    }
}