package com.sabin.onlineshoppingportal.response

import com.sabin.onlineshoppingportal.entity.Cart

data class CartResponse (
        val success : Boolean? = null,
        val message : String? = null,
        val data : Cart? = null,
        )