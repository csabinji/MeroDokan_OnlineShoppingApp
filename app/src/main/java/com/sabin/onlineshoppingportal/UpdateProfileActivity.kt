package com.sabin.onlineshoppingportal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.sabin.onlineshoppingportal.adapter.User
import com.sabin.onlineshoppingportal.api.ServiceBuilder
import com.sabin.onlineshoppingportal.fragment.AccountFragment
import com.sabin.onlineshoppingportal.repository.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UpdateProfileActivity : AppCompatActivity() {

    private lateinit var etxtFname : EditText
    private lateinit var etxtUsername : EditText
    private lateinit var etxtEmail : EditText
    private lateinit var etxtPhone : EditText
    private lateinit var etxtStreet : EditText
    private lateinit var etxtCity : EditText
    private lateinit var etxtZip : EditText
    private lateinit var etxtState : EditText
    private lateinit var imgProfile : ImageView
    private lateinit var btnUpdate : Button

    private var uid : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_profile)

        etxtFname = findViewById(R.id.etxtFname)
        etxtUsername = findViewById(R.id.etxtUsername)
        etxtEmail = findViewById(R.id.etxtEmail)
        etxtPhone = findViewById(R.id.etxtPhone)
        etxtStreet = findViewById(R.id.etxtStreet)
        etxtCity = findViewById(R.id.etxtCity)
        etxtZip = findViewById(R.id.etxtZip)
        etxtState = findViewById(R.id.etxtState)
        imgProfile = findViewById(R.id.imgProfile)
        btnUpdate = findViewById(R.id.btnUpdate)

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val userRepository = UserRepository()
                val response = userRepository.getSingleUser()
                if(response.success==true){

                    val user = response.data!!
                    val image = ServiceBuilder.loadImagePath() + user.image
                    withContext(Dispatchers.Main) {
                        etxtFname.setText("${response.data?.fullname}")
                        etxtUsername.setText("${response.data?.username}")
                        etxtEmail.setText("${response.data?.email}")
                        etxtPhone.setText("${response.data?.phone}")
                        etxtCity.setText("${response.data?.city}")
                        etxtState.setText("${response.data?.state}")
                        etxtStreet.setText("${response.data?.street}")
                        etxtZip.setText("${response.data?.zip}")
                        uid = response.data!!._id.toString()

                        if (!user.image.equals("")) {
                            Glide.with(this@UpdateProfileActivity)
                                .load(image)
                                .fitCenter()
                                .into(imgProfile)
                        }
                    }
                }
            }    catch (ex: Exception) {

            }
        }

        btnUpdate.setOnClickListener {
            val fullname = etxtFname.text.toString()
            val username = etxtUsername.text.toString()
            val email = etxtEmail.text.toString()
            val phone = etxtPhone.text.toString()
            val city = etxtCity.text.toString()
            val street = etxtStreet.text.toString()
            val state = etxtState.text.toString()
            val zip = etxtZip.text.toString()

            val user = User(_id = uid, fullname = fullname, username = username, email = email, phone = phone, city = city, street = street, state = state, zip = zip)

            CoroutineScope(Dispatchers.IO).launch {
                try{
                    val userRepository = UserRepository()
                    val response = userRepository.updateUser(user)
                    if (response.success==true){
                        withContext(Dispatchers.Main){
                            Toast.makeText(this@UpdateProfileActivity, response.message.toString(), Toast.LENGTH_SHORT).show()
                        }
                    }
                }catch (ex: Exception){
                    withContext(Dispatchers.Main){
                        Toast.makeText(this@UpdateProfileActivity, ex.toString(), Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}