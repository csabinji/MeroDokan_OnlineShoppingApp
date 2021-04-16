package com.sabin.ospwearos.api
import com.sabin.ospwearos.response.LoginResponse
import retrofit2.Response
import retrofit2.http.*

interface UserAPI {

    //Login
    @FormUrlEncoded
    @POST("/login")
    suspend fun checkUser(
            @Field("username") username : String,
            @Field("password") password : String
    ) : Response<LoginResponse>
}