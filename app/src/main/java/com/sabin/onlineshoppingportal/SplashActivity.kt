package com.sabin.onlineshoppingportal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import com.sabin.onlineshoppingportal.api.ServiceBuilder
import com.sabin.onlineshoppingportal.repository.UserRepository
import kotlinx.coroutines.*

class SplashActivity : AppCompatActivity() {

    private var username: String = ""
    private var password: String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        getSharedPref()

        if (username != "") {

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
        } else {
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
        val sharedPref = getSharedPreferences("user", MODE_PRIVATE)
        username = sharedPref.getString("username", "").toString()
        password = sharedPref.getString("password", "").toString()
    }
}