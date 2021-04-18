package com.sabin.onlineshoppingportal

import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
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

class SignUpActivity : AppCompatActivity(), SensorEventListener{

    private lateinit var etxtFname : EditText
    private lateinit var etxtEmail : EditText
    private lateinit var etxtUser : EditText
    private lateinit var spnUser : Spinner
    private lateinit var etxtPass : EditText
    private lateinit var etxtRepass : EditText
    private lateinit var btnSignup : Button
    private lateinit var txtLogin : TextView
    private lateinit var selectedItem : String

    private var gyrosensor : Sensor?=null
    private var sensorManager : SensorManager?=null

    private val users = arrayOf("Seller","Buyer")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        etxtFname = findViewById(R.id.etxtFname)
        etxtEmail = findViewById(R.id.etxtEmail)
        etxtUser = findViewById(R.id.etxtUser)
        spnUser = findViewById(R.id.spnUser)
        etxtPass = findViewById(R.id.etxtPass)
        etxtRepass = findViewById(R.id.etxtRepass)
        btnSignup = findViewById(R.id.btnSignup)
        txtLogin = findViewById(R.id.txtLogin)

        txtLogin.setOnClickListener {
            startActivity(
                    Intent(
                            this@SignUpActivity,
                            LoginActivity::class.java
                    )
            )
        }

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
                    }
                }

        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        if (checkgyrosensor()){
            gyrosensor = sensorManager!!.getDefaultSensor(Sensor.TYPE_GYROSCOPE)
            sensorManager!!.registerListener(this, gyrosensor, SensorManager.SENSOR_DELAY_NORMAL)
        }

        btnSignup.setOnClickListener {
            val fullname = etxtFname.text.toString()
            val username = etxtUser.text.toString()
            val email = etxtEmail.text.toString()
            val accountType = selectedItem
            val password = etxtPass.text.toString()
            val repassword = etxtRepass.text.toString()

            if(password != repassword) {
                etxtPass.error = "Password does not matched."
                etxtPass.requestFocus()
                return@setOnClickListener
            }else {
                val user = User(fullname = fullname, username = username, email = email, accountType = accountType, password = password )

                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        val userRepository = UserRepository()
                        val response = userRepository.registerUser(user)
                        if(response.message == "Registration successful."){
                            withContext(Main){
                                startActivity(
                                        Intent(
                                                this@SignUpActivity,
                                                LoginActivity::class.java
                                        )
                                )

                                Toast.makeText(this@SignUpActivity, response.message.toString(), Toast.LENGTH_SHORT).show()
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
    private fun checkgyrosensor(): Boolean {
        var flag = true
        if (sensorManager!!.getDefaultSensor(Sensor.TYPE_GYROSCOPE)==null){
            flag = false
        }
        return flag
    }

    override fun onSensorChanged(event: SensorEvent?) {
        val values = event!!.values[1]
        if(values>5){
            CoroutineScope(Dispatchers.IO).launch {
                try{
                        val intent = Intent(this@SignUpActivity, LoginActivity::class.java)
                        startActivity(intent)
                }catch(ex : Exception) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@SignUpActivity,
                                "Error : $ex", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

    }
}