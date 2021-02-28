package com.sabin.onlineshoppingportal.response

import com.sabin.onlineshoppingportal.entity.Product

data class AddProductResponse (
    val success : Boolean? = null,
    val message: String?=null,
    val data : Product? = null
)