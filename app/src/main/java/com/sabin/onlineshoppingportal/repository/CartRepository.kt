package com.sabin.onlineshoppingportal.repository

import com.sabin.onlineshoppingportal.api.CartAPI
import com.sabin.onlineshoppingportal.api.MyApiRequest
import com.sabin.onlineshoppingportal.api.ServiceBuilder
import com.sabin.onlineshoppingportal.entity.Cart
import com.sabin.onlineshoppingportal.response.CartResponse

class CartRepository :
MyApiRequest() {
    private val CartAPI =
            ServiceBuilder.buildService(CartAPI::class.java)

    // Add to Cart
    suspend fun addtoCart(id : String, cart : Cart) : CartResponse{
        return apiRequest {
            CartAPI.addtoCart(ServiceBuilder.token!!,id, cart)
        }
    }
}