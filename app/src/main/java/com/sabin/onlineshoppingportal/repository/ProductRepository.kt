package com.sabin.onlineshoppingportal.repository

import com.sabin.onlineshoppingportal.api.MyApiRequest
import com.sabin.onlineshoppingportal.api.ProductAPI
import com.sabin.onlineshoppingportal.api.ServiceBuilder
import com.sabin.onlineshoppingportal.entity.Product
import com.sabin.onlineshoppingportal.response.*
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

    suspend fun getAllProducts(): AllProductsResponse {
        return apiRequest {
            productAPI.getAllProducts(
                    ServiceBuilder.token!!
            )
        }
    }

    suspend fun getMyProducts(): MyProductResponse {
        return apiRequest {
            productAPI.getMyProducts(
                ServiceBuilder.token!!
            )
        }
    }

    // Single Product

    suspend fun getProduct(id:String): SingeProductResponse{
        return apiRequest {
            productAPI.getSingleProduct(ServiceBuilder.token!!, id)
        }
    }

    // Delete Product

    suspend fun deleteProduct(product : String) : DeleteProductResponse {
        return apiRequest {
            productAPI.deleteProduct(
                ServiceBuilder.token!!, product
            )
        }
    }
}