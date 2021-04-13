package com.sabin.onlineshoppingportal.response

import com.sabin.onlineshoppingportal.adapter.User

data class UserResponse (
    val success: Boolean?=null,
    val data: User?=null,
)