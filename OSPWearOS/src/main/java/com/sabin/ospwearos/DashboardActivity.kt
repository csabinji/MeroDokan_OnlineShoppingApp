package com.sabin.ospwearos

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.sabin.ospwearos.repository.OrderRepository
import com.sabin.ospwearos.repository.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class DashboardActivity : AppCompatActivity() {

    private lateinit var txtUser : TextView
    private lateinit var txtOrder : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        txtUser = findViewById(R.id.txtUser)
        txtOrder = findViewById(R.id.txtOrder)

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val userRepository = UserRepository()
                val response = userRepository.getSingleUser()
                if (response.success == true) {
                    withContext(Dispatchers.Main) {
                        txtUser.setText("Hello "+"${response.data?.username}" + ", you have")
                        if(response.data?.accountType=="Seller"){
                            val orderRepository = OrderRepository()
                            val response = orderRepository.getSOrder()
                            if (response.success == true) {
                                withContext(Dispatchers.Main) {
                                    txtOrder.setText("${response.length}" + " orders.")

                                }
                            }
                        }else{
                            val orderRepository = OrderRepository()
                            val response = orderRepository.getOrder()
                            if (response.success == true) {
                                withContext(Dispatchers.Main) {
                                    txtOrder.setText("${response.length}" + " orders")

                                }
                            }
                        }
                    }
                }

            } catch(ex : Exception){

            }
        }
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val orderRepository = OrderRepository()
                val response = orderRepository.getSOrder()
                if (response.success == true) {
                    withContext(Dispatchers.Main) {
                        txtOrder.setText("${response.length}" + " orders")

                    }
                }
            } catch(ex : Exception){

            }
        }
    }
}