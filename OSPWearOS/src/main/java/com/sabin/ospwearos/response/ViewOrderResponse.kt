package com.sabin.ospwearos.response

import com.sabin.ospwearos.entity.Order


data class ViewOrderResponse (
    val success: Boolean? = null,
    val message: String?= null,
    val data: ArrayList<Order>? = null,
    val length: String? = null
        )