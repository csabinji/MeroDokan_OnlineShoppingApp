package com.sabin.onlineshoppingportal.repository

import com.sabin.onlineshoppingportal.adapter.User
import com.sabin.onlineshoppingportal.api.MyApiRequest
import com.sabin.onlineshoppingportal.api.ServiceBuilder
import com.sabin.onlineshoppingportal.api.UserAPI
import com.sabin.onlineshoppingportal.response.LoginResponse

class UserRepository
    : MyApiRequest() {

    private val userAPI =
        ServiceBuilder.buildService(UserAPI::class.java)

    // User Registration
    suspend fun registerUser(user: User): LoginResponse {
        return apiRequest {
            userAPI.registerUser(user)
        }
    }
}