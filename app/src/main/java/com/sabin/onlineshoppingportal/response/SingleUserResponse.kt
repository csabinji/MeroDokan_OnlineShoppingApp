package com.sabin.onlineshoppingportal.response

import com.sabin.onlineshoppingportal.adapter.User

data class SingleUserResponse(
        var data : User? = null,
        var success : Boolean? = null,
)