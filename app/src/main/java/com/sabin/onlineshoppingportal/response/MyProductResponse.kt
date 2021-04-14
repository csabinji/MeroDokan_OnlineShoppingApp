package com.sabin.onlineshoppingportal.response

import com.sabin.onlineshoppingportal.entity.Product

data class MyProductResponse (
    val success : Boolean?= null,
    val data: MutableList<Product>? = null
        )