package com.sabin.ospwearos.repository

import com.sabin.ospwearos.api.MyApiRequest
import com.sabin.ospwearos.api.ServiceBuilder
import com.sabin.ospwearos.api.UserAPI
import com.sabin.ospwearos.response.LoginResponse


class UserRepository
    : MyApiRequest() {

    private val userAPI =
        ServiceBuilder.buildService(UserAPI::class.java)

    // User Login
    suspend fun checkUser(username : String, password : String) : LoginResponse {
        return apiRequest {
            userAPI.checkUser(username, password)
        }
    }
}