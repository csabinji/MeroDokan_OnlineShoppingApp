package com.sabin.onlineshoppingportal.response

import com.sabin.onlineshoppingportal.adapter.User

data class UpdateUserResponse (
        val success : Boolean? = null,
        val message : String? = null,
        val data : User? = null,
)