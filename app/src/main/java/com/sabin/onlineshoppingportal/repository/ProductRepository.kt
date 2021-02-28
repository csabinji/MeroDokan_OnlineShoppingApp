package com.sabin.onlineshoppingportal.repository

import com.sabin.onlineshoppingportal.api.MyApiRequest
import com.sabin.onlineshoppingportal.api.ProductAPI
import com.sabin.onlineshoppingportal.api.ServiceBuilder
import com.sabin.onlineshoppingportal.entity.Product
import com.sabin.onlineshoppingportal.response.AddProductResponse
import com.sabin.onlineshoppingportal.response.ImageResponse
import okhttp3.MultipartBody

class ProductRepository :
    MyApiRequest() {
        private val productAPI =
            ServiceBuilder.buildService(ProductAPI::class.java)

    // Add Product
    suspend fun addProduct(product : Product) : AddProductResponse{
        return apiRequest {
            productAPI.addProduct(ServiceBuilder.token!!,product)
        }
    }

    suspend fun uploadImage(id: String, body: MultipartBody.Part)
            : ImageResponse {
        return apiRequest {
            productAPI.uploadImage(ServiceBuilder.token!!, id, body)
        }
    }

    suspend fun getAllProducts(): AllProductResponse {

    }
}