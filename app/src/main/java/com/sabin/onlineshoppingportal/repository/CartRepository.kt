package com.sabin.onlineshoppingportal.repository

import com.sabin.onlineshoppingportal.api.CartAPI
import com.sabin.onlineshoppingportal.api.MyApiRequest
import com.sabin.onlineshoppingportal.api.ServiceBuilder
import com.sabin.onlineshoppingportal.entity.Cart
import com.sabin.onlineshoppingportal.response.AllProductsResponse
import com.sabin.onlineshoppingportal.response.CartResponse
import com.sabin.onlineshoppingportal.response.DeleteCartResponse
import com.sabin.onlineshoppingportal.response.ViewCartResponse

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
    // Cart List

    suspend fun getCart() : ViewCartResponse {
        return apiRequest {
            CartAPI.getCart(
                    ServiceBuilder.token!!
            )
        }
    }

    // Delete Cart

    suspend fun deleteCart(cart : String) : DeleteCartResponse {
        return apiRequest {
            CartAPI.deleteCart(
                    ServiceBuilder.token!!, cart
            )
        }
    }
}