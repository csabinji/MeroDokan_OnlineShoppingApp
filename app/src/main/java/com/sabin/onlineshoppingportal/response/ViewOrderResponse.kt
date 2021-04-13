package com.sabin.onlineshoppingportal.response

import com.sabin.onlineshoppingportal.entity.Order

data class ViewOrderResponse (
    val success: Boolean? = null,
    val message: String?= null,
    val data: ArrayList<Order>? = null
        )