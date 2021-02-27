package com.sabin.onlineshoppingportal.repository

import com.sabin.onlineshoppingportal.adapter.User
import com.sabin.onlineshoppingportal.api.MyApiRequest
import com.sabin.onlineshoppingportal.api.ServiceBuilder
import com.sabin.onlineshoppingportal.api.UserAPI
import com.sabin.onlineshoppingportal.response.LoginResponse
import com.sabin.onlineshoppingportal.response.SignupResponse

class UserRepository
    : MyApiRequest() {

    private val userAPI =
        ServiceBuilder.buildService(UserAPI::class.java)

    // User Registration
    suspend fun registerUser(user: User): SignupResponse {
        return apiRequest {
            userAPI.registerUser(user)
        }
    }
    // User Login
    suspend fun checkUser(username : String, password : String) : LoginResponse{
        return apiRequest {
            userAPI.checkUser(username, password)
        }
    }
}