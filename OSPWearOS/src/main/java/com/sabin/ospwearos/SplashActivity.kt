package com.sabin.ospwearos

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sabin.ospwearos.api.ServiceBuilder
import com.sabin.ospwearos.repository.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity : AppCompatActivity() {

    private var username : String = ""
    private var password : String= ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        getSharedPref()

        if(username!="") {

            CoroutineScope(Dispatchers.IO).launch {

                val repository = UserRepository()
                val response = repository.checkUser(username, password)
                if (response.success == true) {
                    ServiceBuilder.token = "Bearer " + response.token
                    ServiceBuilder.accountType = response.accountType
                    startActivity(
                        Intent(
                            this@SplashActivity,
                            DashboardActivity::class.java
                        )
                    )
                    finish()
                    delay(1000)
                }
            }
        }else{
            startActivity(
                Intent(
                    this@SplashActivity,
                    LoginActivity::class.java
                )
            )
            finish()
        }
    }
    private fun getSharedPref() {
        val sharedPref =  getSharedPreferences("user", MODE_PRIVATE)
        username = sharedPref.getString("username","").toString()
        password = sharedPref.getString("password","").toString()
    }
}