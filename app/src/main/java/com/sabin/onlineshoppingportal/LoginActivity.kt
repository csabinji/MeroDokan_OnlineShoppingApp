package com.sabin.onlineshoppingportal

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.core.app.ActivityCompat
import com.google.android.material.snackbar.Snackbar
import com.sabin.onlineshoppingportal.adapter.User
import com.sabin.onlineshoppingportal.api.ServiceBuilder
import com.sabin.onlineshoppingportal.repository.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginActivity : AppCompatActivity(), SensorEventListener{

    private lateinit var etxtUser : EditText
    private lateinit var etxtPass : EditText
    private lateinit var linearlayout : LinearLayout
    private lateinit var btnLogin : Button
    private lateinit var txtSignup : TextView

    private var sensor : Sensor?=null
    private var sensorManager : SensorManager?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        etxtUser = findViewById(R.id.etxtUser)
        etxtPass = findViewById(R.id.etxtPass)
        linearlayout = findViewById(R.id.linearLayout)
        btnLogin = findViewById(R.id.btnLogin)
        txtSignup = findViewById(R.id.txtSignup)


        txtSignup.setOnClickListener {
            startActivity(Intent(this,SignUpActivity::class.java))
        }
        btnLogin.setOnClickListener {
            login()
        }

        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        if (checksensor()){
            sensor = sensorManager!!.getDefaultSensor(Sensor.TYPE_LIGHT)
            sensorManager!!.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL)
        }

    }

    private fun login() {
        val username = etxtUser.text.toString()
        val password = etxtPass.text.toString()
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val repository = UserRepository()
                val response = repository.checkUser(username, password)
                if (response.success == true) {
                    ServiceBuilder.token = "Bearer " + response.token
                    ServiceBuilder.accountType = response.accountType
                    saveMe()
                    startActivity(
                            Intent(
                                    this@LoginActivity,
                                    DashboardActivity::class.java
                            )
                    )
                    finish()
                } else {
                    withContext(Dispatchers.Main) {
                        val snack =
                                Snackbar.make(
                                        linearlayout,
                                        "Invalid credentials",
                                        Snackbar.LENGTH_LONG
                                )
                        snack.setAction("OK", View.OnClickListener {
                            snack.dismiss()
                        })
                        snack.show()
                    }
                }

            } catch (ex: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                            this@LoginActivity,
                            "Login error", Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun saveMe(){
        val username = etxtUser.text.toString()
        val password = etxtPass.text.toString()
        val sharedPref = getSharedPreferences("user", MODE_PRIVATE)
        val editor = sharedPref.edit()
       // editor.putBoolean("isLoggedIn", true)
        editor.putString("username", username)
        editor.putString("password", password)
        editor.apply()
    }

    private fun checksensor(): Boolean {
        var flag = true
        if (sensorManager!!.getDefaultSensor(Sensor.TYPE_LIGHT)==null){
            flag = false
        }
        return flag
    }

    override fun onSensorChanged(event: SensorEvent?) {
        val values = event!!.values[0]
        if(values > 40)
        {
            linearlayout.setBackgroundColor(Color.GRAY)
        }
        if (values < 40){
            linearlayout.setBackgroundColor(Color.WHITE)
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }
}