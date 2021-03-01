package com.sabin.onlineshoppingportal.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.sabin.onlineshoppingportal.dao.ProductDAO
import com.sabin.onlineshoppingportal.entity.Product
import kotlinx.coroutines.internal.synchronized

@Database(
        entities = [(Product::class)],
        version = 1,
        exportSchema = false
)

abstract class ProductDB: RoomDatabase() {
    abstract fun getProductDAO() : ProductDAO
    companion object{
        @Volatile
        private var instance : ProductDB? = null
        fun getInstance(context: Context) : ProductDB{
            if(instance==null){
                kotlin.synchronized(ProductDB::class.java) {
                    instance = buildDatabase(context)
                }
            }

        return instance!!
    }
    private fun buildDatabase(context : Context) =
            Room.databaseBuilder(
                    context.applicationContext,
                    ProductDB::class.java,
                    "ProductDB"
            ).build()
}
}
