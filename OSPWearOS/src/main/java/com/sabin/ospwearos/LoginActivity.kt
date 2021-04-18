package com.sabin.ospwearos

import android.content.Intent
import android.os.Bundle
import android.support.wearable.activity.WearableActivity
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import com.sabin.ospwearos.api.ServiceBuilder
import com.sabin.ospwearos.repository.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class LoginActivity : WearableActivity() {

    private lateinit var etxtUsername : EditText
    private lateinit var etxtPassword : EditText
    private lateinit var linearlayout : LinearLayout
    private lateinit var btnLogin : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        etxtUsername = findViewById(R.id.etxtUsername)
        etxtPassword = findViewById(R.id.etxtPassword)
        linearlayout = findViewById(R.id.linearlayout)
        btnLogin = findViewById(R.id.btnLogin)

        btnLogin.setOnClickListener {
            userLogin()
        }

        // Enables Always-on
        setAmbientEnabled()
    }

    private fun userLogin(){
        val username = etxtUsername.text.toString()
        val password = etxtPassword.text.toString()

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val repository = UserRepository()
                val response = repository.checkUser(username,password)
                if(response.success == true) {
                    ServiceBuilder.token = "Bearer " + response.token
                    ServiceBuilder.accountType = response.accountType
                    startActivity(
                        Intent(
                            this@LoginActivity,
                            DashboardActivity::class.java
                        )
                    )
                    finish()
                }
            }catch (ex : Exception){
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        this@LoginActivity,
                        "Login error", Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}