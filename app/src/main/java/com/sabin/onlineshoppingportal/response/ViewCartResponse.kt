package com.sabin.onlineshoppingportal.response

import com.sabin.onlineshoppingportal.entity.Cart

data class ViewCartResponse (
        val success: Boolean? = null,
        val message: String?= null,
        val data: ArrayList<Cart>? = null
        )