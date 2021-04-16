package com.sabin.ospwearos.repository

import androidx.room.FtsOptions
import com.sabin.ospwearos.api.MyApiRequest
import com.sabin.ospwearos.api.ServiceBuilder
import com.sabin.ospwearos.response.ViewOrderResponse
import com.sabin.ospwearos.api.OrderAPI

class OrderRepository :
MyApiRequest() {
    private val OrderAPI =
        ServiceBuilder.buildService(OrderAPI::class.java)

    // Order List for Buyer

    suspend fun getOrder() : ViewOrderResponse {
        return apiRequest {
            OrderAPI.getOrder(
                ServiceBuilder.token!!
            )
        }
    }

    // Order List for Seller

    suspend fun getSOrder() : ViewOrderResponse {
        return apiRequest {
            OrderAPI.getSOrder(
                    ServiceBuilder.token!!
            )
        }
    }
}