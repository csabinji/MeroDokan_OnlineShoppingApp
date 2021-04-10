package com.sabin.onlineshoppingportal.api

import com.sabin.onlineshoppingportal.adapter.User
import com.sabin.onlineshoppingportal.response.AllProductsResponse
import com.sabin.onlineshoppingportal.response.LoginResponse
import com.sabin.onlineshoppingportal.response.SignupResponse
import com.sabin.onlineshoppingportal.response.SingleUserResponse
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
}