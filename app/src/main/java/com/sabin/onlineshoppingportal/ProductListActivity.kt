package com.sabin.onlineshoppingportal

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sabin.onlineshoppingportal.adapter.OrderAdapter
import com.sabin.onlineshoppingportal.adapter.ProductListAdapter
import com.sabin.onlineshoppingportal.entity.Order
import com.sabin.onlineshoppingportal.entity.Product
import com.sabin.onlineshoppingportal.repository.ProductRepository
import com.sabin.onlineshoppingportal.repository.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProductListActivity : AppCompatActivity() {
    private lateinit var topRecycler : RecyclerView
    private var lstProduct = mutableListOf<Product>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_list)

        topRecycler = findViewById(R.id.topRecycler)

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val productRepository = ProductRepository()
                val response = productRepository.getMyProducts()

                if(response.success == true) {
                    lstProduct = response.data!!
                    withContext(Dispatchers.Main) {
                        topRecycler.adapter = ProductListAdapter(lstProduct, this@ProductListActivity!!)
                        topRecycler.layoutManager = LinearLayoutManager(this@ProductListActivity)
                    }
                }
            } catch (ex : Exception){

            }
        }

    }
}