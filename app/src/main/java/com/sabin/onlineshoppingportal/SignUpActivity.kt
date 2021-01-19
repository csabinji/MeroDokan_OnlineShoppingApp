package com.sabin.onlineshoppingportal

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner

class SignUpActivity : AppCompatActivity() {

    private lateinit var etxtName : EditText
    private lateinit var etxtEmail : EditText
    private lateinit var etxtPhone : EditText
    private lateinit var etxtUser : EditText
    private lateinit var spnUser : Spinner
    private lateinit var etxtPass : EditText
    private lateinit var etxtRepass : EditText
    private lateinit var btnSignup : Button

    private val users = arrayOf("Seller","Buyer")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        etxtName = findViewById(R.id.etxtName)
        etxtEmail = findViewById(R.id.etxtEmail)
        etxtPhone = findViewById(R.id.etxtPhone)
        etxtUser = findViewById(R.id.etxtUser)
        spnUser = findViewById(R.id.spnUser)
        etxtPass = findViewById(R.id.etxtPass)
        etxtRepass = findViewById(R.id.etxtRepass)
        btnSignup = findViewById(R.id.btnSignup)

        val adapter = ArrayAdapter(
                this,
                android.R.layout.simple_list_item_1,
                users
        )
        spnUser.adapter = adapter
    }
}