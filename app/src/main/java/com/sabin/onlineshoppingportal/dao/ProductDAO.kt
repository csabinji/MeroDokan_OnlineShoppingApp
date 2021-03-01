package com.sabin.onlineshoppingportal.dao

import androidx.room.Dao
import androidx.room.Insert
import com.sabin.onlineshoppingportal.entity.Product
@Dao
interface ProductDAO {
    @Insert
    suspend fun insertProduct(product: Product)
}