package com.sabin.onlineshoppingportal.api

import com.sabin.onlineshoppingportal.entity.Product
import com.sabin.onlineshoppingportal.response.AddProductResponse
import com.sabin.onlineshoppingportal.response.AllProductsResponse
import com.sabin.onlineshoppingportal.response.ImageResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*

interface ProductAPI {

    //Add Product
    @POST("/product/insert")
    suspend fun addProduct(
        @Header("Authorization") token : String,
        @Body product : Product
    ) : Response<AddProductResponse>

    @Multipart
    @PUT("product/{id}/image")
    suspend fun uploadImage(
        @Header("Authorization") token: String,
        @Path("id") id: String,
        @Part file: MultipartBody.Part
    ): Response<ImageResponse>

    //get all products
    @GET("/product/fetch")
    suspend fun getAllProducts(
            @Header("Authorization") token : String
    ) : Response<AllProductsResponse>
}