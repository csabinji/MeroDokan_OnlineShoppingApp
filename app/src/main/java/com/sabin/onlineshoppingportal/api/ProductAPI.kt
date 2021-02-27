package com.sabin.onlineshoppingportal.api

import com.sabin.onlineshoppingportal.entity.Product
import com.sabin.onlineshoppingportal.response.AddProductResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface ProductAPI {

    //Add Product
    @POST("/product/insert")
    suspend fun addProduct(
        @Header("Authorization") token : String,
        @Body product : Product
    ) : Response<AddProductResponse>
}