package com.sabin.onlineshoppingportal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.sabin.onlineshoppingportal.adapter.User
import com.sabin.roomdatabaseactivity.db.UserDB
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SignUpActivity : AppCompatActivity(){

    private lateinit var etxtName : EditText
    private lateinit var etxtEmail : EditText
    private lateinit var etxtPhone : EditText
    private lateinit var etxtUser : EditText
    private lateinit var spnUser : Spinner
    private lateinit var etxtPass : EditText
    private lateinit var etxtRepass : EditText
    private lateinit var btnSignup : Button
    private lateinit var txtLogin : TextView

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
        txtLogin = findViewById(R.id.txtLogin)

        val adapter = ArrayAdapter(
                this,
                android.R.layout.simple_list_item_1,
                users
        )
        spnUser.adapter = adapter

        btnSignup.setOnClickListener {
            val name = etxtName.text.toString()
            val email = etxtEmail.text.toString()
            val phone = etxtPhone.text.toString()
            val username = etxtUser.text.toString()
            val usertype = spnUser.toString()
            val password = etxtPass.text.toString()
            val repassword = etxtRepass.text.toString()
            if(password != repassword) {
                etxtPass.error = "Password does not matched."
                etxtPass.requestFocus()
                return@setOnClickListener
            }else {
                val user = User(name, email, phone, username, usertype, password )
                CoroutineScope(Dispatchers.IO).launch {
                    UserDB
                            .getInstance(this@SignUpActivity)
                            .getUserDao()
                            .registerUser(user)
                    withContext(Main){
                        Toast.makeText(this@SignUpActivity, "ser Registered Sucessfully", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

}