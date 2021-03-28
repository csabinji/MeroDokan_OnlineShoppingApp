package com.sabin.onlineshoppingportal.response

import com.sabin.onlineshoppingportal.entity.Product

data class AllProductsResponse (
        val success: Boolean? = null,
        val data: MutableList<Product>? = null
        )