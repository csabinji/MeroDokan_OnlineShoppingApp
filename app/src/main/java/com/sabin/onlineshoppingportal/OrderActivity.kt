package com.sabin.onlineshoppingportal

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sabin.onlineshoppingportal.adapter.CartAdapter
import com.sabin.onlineshoppingportal.adapter.OrderAdapter
import com.sabin.onlineshoppingportal.entity.Order
import com.sabin.onlineshoppingportal.repository.OrderRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class OrderActivity : AppCompatActivity() {

    private lateinit var topRecycler : RecyclerView
    private var lstOrder = mutableListOf<Order>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order)

        topRecycler = findViewById(R.id.topRecycler)

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val orderRepository = OrderRepository()
                val response = orderRepository.getOrder()
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@OrderActivity,response.success.toString(), Toast.LENGTH_SHORT)
                            .show()
                }
                if (response.success == true) {
                    lstOrder = response.data!!
                    withContext(Dispatchers.Main) {
                        topRecycler.adapter = OrderAdapter(lstOrder, this@OrderActivity!!)
                        topRecycler.layoutManager = LinearLayoutManager(this@OrderActivity)
                    }
                }
                else{
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@OrderActivity,"", Toast.LENGTH_SHORT)
                                .show()
                    }
                }
            } catch (ex: Exception) {
                withContext(Dispatchers.Main) {
                    Log.d("Error", ex.localizedMessage)
                    Toast.makeText(
                            this@OrderActivity,
                            ex.localizedMessage,
                            Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}