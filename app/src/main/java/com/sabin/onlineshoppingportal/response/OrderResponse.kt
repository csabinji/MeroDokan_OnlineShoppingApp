package com.sabin.onlineshoppingportal.response

import com.sabin.onlineshoppingportal.entity.Order

data class OrderResponse (
        val success : Boolean? = null,
        val message : String? = null,
        val data : Order? = null,
        )