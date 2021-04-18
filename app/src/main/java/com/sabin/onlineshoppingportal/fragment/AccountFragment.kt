package com.sabin.onlineshoppingportal.fragment

import android.content.Context.MODE_PRIVATE
import android.content.Context.SENSOR_SERVICE
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.sabin.onlineshoppingportal.*
import com.sabin.onlineshoppingportal.api.ServiceBuilder
import com.sabin.onlineshoppingportal.repository.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AccountFragment : Fragment(), SensorEventListener {

    private lateinit var txtusername : TextView
    private lateinit var txtFname : TextView
    private lateinit var txtemail : TextView
    private lateinit var txtType : TextView
    private lateinit var txtStreet : TextView
    private lateinit var txtCity : TextView
    private lateinit var txtZip : TextView
    private lateinit var txtState : TextView
    private lateinit var imgProfile : ImageView
    private lateinit var btnEdit : Button
    private lateinit var btnOrder : Button
    private lateinit var btnProduct : Button
    private lateinit var btnLogout : Button

    private var gyrosensor : Sensor?=null
    private var sensorManager : SensorManager?=null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.fragment_account, container, false)

        txtusername = view.findViewById(R.id.txtusername)
        txtFname = view.findViewById(R.id.txtFname)
        txtemail = view.findViewById(R.id.txtemail)
        txtType = view.findViewById(R.id.txtType)
        txtStreet = view.findViewById(R.id.txtStreet)
        txtState = view.findViewById(R.id.txtState)
        txtCity = view.findViewById(R.id.txtCity)
        txtZip = view.findViewById(R.id.txtZip)
        imgProfile = view.findViewById(R.id.imgProfile)
        btnEdit = view.findViewById(R.id.btnEdit)
        btnOrder = view.findViewById(R.id.btnOrder)
        btnProduct = view.findViewById(R.id.btnProduct)
        btnLogout = view.findViewById(R.id.btnLogout)

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val userRepository = UserRepository()
                val response = userRepository.getSingleUser()
                if (response.success == true) {

                    val user = response.data!!
                    val image = ServiceBuilder.loadImagePath() + user.image
                    withContext(Dispatchers.Main) {
                        txtusername.setText("${response.data?.username}")
                        txtFname.setText("${response.data?.fullname}")
                        txtemail.setText("${response.data?.email}")
                        txtType.setText("${response.data?.phone}")
                        txtStreet.setText("${response.data?.street}")
                        txtState.setText("${response.data?.state}")
                        txtCity.setText("${response.data?.city}")
                        txtZip.setText("${response.data?.zip}")

                        if (!user.image.equals("")) {
                            Glide.with(context!!)
                                .load(image)
                                .fitCenter()
                                .into(imgProfile)
                        }
                    }
                }
            } catch (ex: Exception) {

            }
        }

        btnEdit.setOnClickListener {
            val intent = Intent(activity, UpdateProfileActivity::class.java)
            startActivity(intent)
        }

        btnOrder.setOnClickListener {
            val intent = Intent(activity, OrderActivity::class.java)
            startActivity(intent)
        }

        if(ServiceBuilder.accountType=="Seller"){
            btnProduct.visibility=View.VISIBLE
            btnProduct.setOnClickListener {
                val intent = Intent(activity, ProductListActivity::class.java)
                startActivity(intent)
            }
        }

        btnLogout.setOnClickListener {
            ServiceBuilder.token.equals("")
            ServiceBuilder.accountType.equals("")
            getSharedPref()
            startActivity(
                Intent(
               context!!,
                    LoginActivity::class.java
                )
            )
        }

        sensorManager = requireContext().getSystemService(SENSOR_SERVICE) as SensorManager
        if (checkgyrosensor()){
            gyrosensor = sensorManager!!.getDefaultSensor(Sensor.TYPE_GYROSCOPE)
            sensorManager!!.registerListener(this, gyrosensor, SensorManager.SENSOR_DELAY_NORMAL)
        }

        return view
    }

    private fun checkgyrosensor(): Boolean {
        var flag = true
        if (sensorManager!!.getDefaultSensor(Sensor.TYPE_GYROSCOPE)==null){
            flag = false
        }
        return flag
    }

    private fun getSharedPref() {
        val sharedPref = requireContext().getSharedPreferences("user", MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.clear()
        editor.apply()
    }

    override fun onSensorChanged(event: SensorEvent?) {
        val values = event!!.values[1]
        if (values > 5) {
            CoroutineScope(Dispatchers.IO).launch {
                try{
                    ServiceBuilder.token.equals("")
                    ServiceBuilder.accountType.equals("")
                    getSharedPref()
                    startActivity(
                            Intent(
                                    context!!,
                                    LoginActivity::class.java
                            )
                    )
                }catch (ex : Exception){
                }
            }
        }
    }
    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        TODO("Not yet implemented")
    }
}