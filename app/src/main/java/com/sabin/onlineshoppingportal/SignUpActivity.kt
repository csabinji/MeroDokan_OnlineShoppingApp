package com.sabin.onlineshoppingportal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.sabin.onlineshoppingportal.adapter.User
import com.sabin.onlineshoppingportal.repository.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SignUpActivity : AppCompatActivity(){

    private lateinit var etxtFname : EditText
    private lateinit var etxtLname : EditText
    private lateinit var etxtEmail : EditText
    private lateinit var etxtAddress : EditText
    private lateinit var etxtPhone : EditText
    private lateinit var etxtUser : EditText
    private lateinit var spnUser : Spinner
    private lateinit var etxtPass : EditText
    private lateinit var etxtRepass : EditText
    private lateinit var btnSignup : Button
    private lateinit var txtLogin : TextView
    private lateinit var selectedItem : String

    private val users = arrayOf("Seller","Buyer")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        etxtFname = findViewById(R.id.etxtFname)
        etxtLname = findViewById(R.id.etxtLname)
        etxtEmail = findViewById(R.id.etxtEmail)
        etxtPhone = findViewById(R.id.etxtPhone)
        etxtUser = findViewById(R.id.etxtUser)
        etxtAddress = findViewById(R.id.etxtAddress)
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

        spnUser.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onNothingSelected(parent: AdapterView<*>?) {
                        TODO("Not yet implemented")
                    }

                    override fun onItemSelected(
                            parent: AdapterView<*>?, view: View?, position: Int, id: Long
                    ) {
                        selectedItem = parent?.getItemAtPosition(position).toString()
                        Toast.makeText(this@SignUpActivity, "$selectedItem", Toast.LENGTH_SHORT).show()
                    }
                }

        btnSignup.setOnClickListener {
            val fname = etxtFname.text.toString()
            val lname = etxtLname.text.toString()
            val email = etxtEmail.text.toString()
            val phone = etxtPhone.text.toString()
            val address = etxtAddress.text.toString()
            val username = etxtUser.text.toString()
            val accountType = selectedItem
            val password = etxtPass.text.toString()
            val repassword = etxtRepass.text.toString()

            if(password != repassword) {
                etxtPass.error = "Password does not matched."
                etxtPass.requestFocus()
                return@setOnClickListener
            }else {
                val user = User(fname = fname, lname = lname, username = username, email = email, phone = phone, address = address, accountType = accountType, password = password )

                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        val userRepository = UserRepository()
                        val response = userRepository.registerUser(user)
                        if(response.success == true){
                            withContext(Main){
                                Toast.makeText(this@SignUpActivity, "Registered Successful", Toast.LENGTH_SHORT).show()
                                startActivity(
                                        Intent(
                                                this@SignUpActivity,
                                                LoginActivity::class.java
                                        )
                                )
                            }
                        }

                    }catch (ex: Exception){
                        withContext(Main){
                            Toast.makeText(this@SignUpActivity, ex.toString(), Toast.LENGTH_SHORT).show()
                        }
                    }
                }

            }
        }
    }

}