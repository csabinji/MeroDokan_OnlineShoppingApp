package com.sabin.onlineshoppingportal.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.sabin.onlineshoppingportal.entity.Product
@Dao
interface ProductDAO {
    @Insert
    suspend fun insertProduct(product: Product)

    @Query("SELECT * FROM Product")
    suspend fun getAllProduct(): MutableList<Product>

    @Query("DELETE FROM Product")
    suspend fun deleteAll()
}