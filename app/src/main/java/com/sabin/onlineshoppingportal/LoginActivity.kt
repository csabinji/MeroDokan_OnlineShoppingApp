package com.sabin.onlineshoppingportal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*

class LoginActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var etxtUser : EditText
    private lateinit var etxtPass : EditText
    private lateinit var btnLogin : Button
    private lateinit var txtSignup : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        etxtUser = findViewById(R.id.etxtUser)
        etxtPass = findViewById(R.id.etxtPass)
        btnLogin = findViewById(R.id.btnLogin)
        txtSignup = findViewById(R.id.txtSignup)

        txtSignup.setOnClickListener(this)
        btnLogin.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        when (v?.id){
            R.id.txtSignup -> {
                val intent = Intent(this,SignUpActivity::class.java)
                startActivity(intent)
            }
            R.id.btnLogin -> {
                val intent = Intent(this,DashboardActivity::class.java)
                startActivity(intent)
            }
        }
    }
}