package com.sabin.ospwearos.api
import com.sabin.ospwearos.response.LoginResponse
import com.sabin.ospwearos.response.SingleUserResponse
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

    //Fetch
    @GET("user/fetch/single")
    suspend fun getSingleUser(
        @Header("Authorization") token : String
    ) : Response<SingleUserResponse>
}