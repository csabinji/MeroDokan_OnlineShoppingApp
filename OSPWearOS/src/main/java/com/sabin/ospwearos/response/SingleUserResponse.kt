package com.sabin.ospwearos.response

import com.sabin.ospwearos.entity.User

data class SingleUserResponse(
        var data : User? = null,
        var success : Boolean? = null,
)