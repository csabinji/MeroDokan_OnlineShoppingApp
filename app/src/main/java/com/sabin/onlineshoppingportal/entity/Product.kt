package com.sabin.onlineshoppingportal.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Product (
    val _id : String? = null,
    val name : String? = null,
    val dec : String? = null,
    val price : String? = null,
    val category : String? = null,
    val image : String? = null
        ){
        @PrimaryKey(autoGenerate = true)
        var productID : Int = 0
}