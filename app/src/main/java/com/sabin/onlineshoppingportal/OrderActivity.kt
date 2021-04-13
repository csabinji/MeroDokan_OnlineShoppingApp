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
import com.sabin.onlineshoppingportal.repository.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class OrderActivity : AppCompatActivity() {

    private lateinit var topRecycler : RecyclerView
    private var lstOrder = mutableListOf<Order>()
    private var isSeller:Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order)

        topRecycler = findViewById(R.id.topRecycler)

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val userRepository = UserRepository()
                val response = userRepository.getSingleUser()
                if(response.success == true){
                    if(response.data?.accountType=="Seller"){
                        isSeller=true
                        val orderRepository = OrderRepository()
                        val response = orderRepository.getSOrder()
                        if (response.success == true) {
                            lstOrder = response.data!!
                            withContext(Dispatchers.Main) {
                                topRecycler.adapter = OrderAdapter(lstOrder, this@OrderActivity!!)
                                topRecycler.layoutManager = LinearLayoutManager(this@OrderActivity)
                            }
                        }
                    }
                    if(response.data?.accountType=="Buyer"){
                        isSeller=false
                        val orderRepository = OrderRepository()
                        val response = orderRepository.getOrder()
                        if (response.success == true) {
                            lstOrder = response.data!!
                            withContext(Dispatchers.Main) {
                                topRecycler.adapter = OrderAdapter(lstOrder, this@OrderActivity!!)
                                topRecycler.layoutManager = LinearLayoutManager(this@OrderActivity)
                            }
                        }
                    }
                }

            } catch (ex: Exception) {

            }
        }

//        CoroutineScope(Dispatchers.IO).launch {
//            withContext(Dispatchers.Main) {
////                Toast.makeText(this@OrderActivity, at, Toast.LENGTH_SHORT)
////                        .show()
//            }
//            try {
////                if (at != "Seller") {
////                    withContext(Dispatchers.Main) {
////                        Toast.makeText(this@OrderActivity, "You are Seller", Toast.LENGTH_SHORT)
////                                .show()
////                                }
////                }
////                if(at=="Buyer"){
////                    val orderRepository = OrderRepository()
////                    val response = orderRepository.getOrder()
////                    withContext(Dispatchers.Main) {
////                        Toast.makeText(this@OrderActivity, at.toString(), Toast.LENGTH_SHORT)
////                                .show()
////                    }
////                    if (response.success == true) {
////                        lstOrder = response.data!!
////                        withContext(Dispatchers.Main) {
////                            topRecycler.adapter = OrderAdapter(lstOrder, this@OrderActivity!!)
////                            topRecycler.layoutManager = LinearLayoutManager(this@OrderActivity)
////                        }
////                    }
////                }
//                } catch (ex: Exception) {
//                    withContext(Dispatchers.Main) {
//                        Log.d("Error", ex.localizedMessage)
//                        Toast.makeText(
//                                this@OrderActivity,
//                                ex.localizedMessage,
//                                Toast.LENGTH_SHORT
//                        ).show()
//                    }
//                }
//            }
//
        }
    }
