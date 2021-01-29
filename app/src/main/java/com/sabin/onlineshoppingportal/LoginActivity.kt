package com.sabin.onlineshoppingportal

import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.core.app.ActivityCompat
import com.sabin.onlineshoppingportal.adapter.User
import com.sabin.onlineshoppingportal.db.UserDB
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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
                saveSharedPref()
                val username = etxtUser.text.toString()
                val password = etxtPass.text.toString()
                var user: User? = null
                CoroutineScope(Dispatchers.IO).launch {
                    user = UserDB
                            .getInstance(this@LoginActivity)
                            .getUserDao()
                            .checkUser(username,password)
                    if(user == null){
                        withContext(Dispatchers.Main){
                            Toast.makeText(this@LoginActivity, "Invalid credentials", Toast.LENGTH_SHORT)
                                    .show()
                        }
                    }else{
                        startActivity(Intent(this@LoginActivity,
                                DashboardActivity::class.java))
                    }
                }
            }
        }
    }
    private fun saveSharedPref() {
        val username = etxtUser.text.toString()
        val password = etxtPass.text.toString()
        val sharedPref = getSharedPreferences("MyPref", MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putString("username", username)
        editor.putString("password", password)
        editor.apply()
        Toast.makeText(
                this@LoginActivity,
                "Username and password saved",
                Toast.LENGTH_SHORT
        ).show()
    }
}