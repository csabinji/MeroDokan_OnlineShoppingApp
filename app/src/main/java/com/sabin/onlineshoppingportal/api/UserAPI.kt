package com.sabin.onlineshoppingportal.api

import com.sabin.onlineshoppingportal.adapter.User
import com.sabin.onlineshoppingportal.response.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface UserAPI {

    //SignUp
    @POST("/signup")
    suspend fun registerUser(
        @Body user : User
    ) : Response<LoginResponse>

    //Login
    @FormUrlEncoded
    @POST("/login")
    suspend fun checkUser(
            @Field("username") username : String,
            @Field("password") password : String
    ) : Response<LoginResponse>
}