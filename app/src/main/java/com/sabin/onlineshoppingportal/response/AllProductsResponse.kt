package com.sabin.onlineshoppingportal.response

import com.sabin.onlineshoppingportal.entity.Product

data class AllProductsResponse (
        val success: Boolean? = true,
        val ProductData: MutableList<Product>? = null
        )