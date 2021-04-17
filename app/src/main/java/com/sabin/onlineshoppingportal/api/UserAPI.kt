package com.sabin.onlineshoppingportal.api

import com.sabin.onlineshoppingportal.adapter.User
import com.sabin.onlineshoppingportal.response.*
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*

interface UserAPI {

    //SignUp
    @POST("/signup")
    suspend fun registerUser(
        @Body user : User
    ) : Response<SignupResponse>

    //Login
    @FormUrlEncoded
    @POST("/login")
    suspend fun checkUser(
            @Field("username") username : String,
            @Field("password") password : String
    ) : Response<LoginResponse>

    //Fetch
    @GET("user/fetch/single")
    suspend fun getSingleUser(
            @Header("Authorization") token : String
    ) : Response<SingleUserResponse>

    //Update
    @PUT("/auser/update")
    suspend fun UpdateUser(
            @Header("Authorization") token : String,
            @Body user : User
    ) : Response<UpdateUserResponse>

    @GET("/user/fetch/single/{id}")
    suspend fun getBuyer(
            @Header("Authorization") token: String,
            @Path("id") id: String
    ) : Response<UserResponse>

    @Multipart
    @PUT("user/{id}/image")
    suspend fun uploadUserImage(
            @Header("Authorization") token: String,
            @Path("id") id: String,
            @Part file: MultipartBody.Part
    ): Response<ImageResponse>


}