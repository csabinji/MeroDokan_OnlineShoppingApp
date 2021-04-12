package com.sabin.onlineshoppingportal.response

import com.sabin.onlineshoppingportal.entity.Product

data class SingeProductResponse (
        val success: Boolean?=null,
        val data: Product?=null,
        )