package com.sabin.onlineshoppingportal

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.*
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.bumptech.glide.Glide
import com.sabin.onlineshoppingportal.adapter.User
import com.sabin.onlineshoppingportal.api.ServiceBuilder
import com.sabin.onlineshoppingportal.fragment.AccountFragment
import com.sabin.onlineshoppingportal.notification.NotificationChannels
import com.sabin.onlineshoppingportal.repository.ProductRepository
import com.sabin.onlineshoppingportal.repository.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*

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

        imgProfile.setOnClickListener {
            loadPopUpMenu()
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
                        if(imageUrl != null){

                            uploadImage(response.data!!._id!!)
                        }
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
    private fun uploadImage(uId: String) {
        if (imageUrl != null) {
            val file = File(imageUrl!!)
            val reqFile =
                    RequestBody.create(MediaType.parse("image/" + file.extension.toLowerCase().replace("jpg","jpeg")),file)
            val body =
                    MultipartBody.Part.createFormData("image", file.name, reqFile)
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val userRepository  = UserRepository()
                    val response = userRepository.uploadImage(uId, body)
                    if (response.success == true) {
                        withContext(Dispatchers.Main) {

                        }
                    }
                    else{
                        withContext(Dispatchers.Main) {
                            Toast.makeText(this@UpdateProfileActivity, response.message.toString(), Toast.LENGTH_SHORT)
                                    .show()
                        }
                    }
                } catch (ex: Exception) {
                    withContext(Dispatchers.Main) {
                        Log.d("Mero Error ", ex.localizedMessage)
                        Toast.makeText(
                                this@UpdateProfileActivity,
                                ex.localizedMessage,
                                Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }

    }

    private fun loadPopUpMenu() {
        val popupMenu = PopupMenu(this, imgProfile)
        popupMenu.menuInflater.inflate(R.menu.gallery_camera, popupMenu.menu)
        popupMenu.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.menuCamera ->
                    openCamera()
                R.id.menuGallery ->
                    openGallery()
            }
            true
        }
        popupMenu.show()
    }



    private var REQUEST_GALLERY_CODE = 0
    private var REQUEST_CAMERA_CODE = 1
    private var imageUrl: String? = null

    private fun openCamera() {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(cameraIntent, REQUEST_CAMERA_CODE)
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, REQUEST_GALLERY_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_GALLERY_CODE && data != null) {
                val selectedImage = data.data
                val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)
                val contentResolver = this.contentResolver
                val cursor =
                        contentResolver?.query(selectedImage!!, filePathColumn, null, null, null)
                cursor!!.moveToFirst()
                val columnIndex = cursor.getColumnIndex(filePathColumn[0])
                imageUrl = cursor.getString(columnIndex)
                imgProfile.setImageBitmap(BitmapFactory.decodeFile(imageUrl))
                cursor.close()
            } else if (requestCode == REQUEST_CAMERA_CODE && data != null) {
                val imageBitmap = data.extras?.get("data") as Bitmap
                val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
                val file = bitmapToFile(imageBitmap, "$timeStamp.jpg")
                imageUrl = file!!.absolutePath
                imgProfile.setImageBitmap(BitmapFactory.decodeFile(imageUrl))
            }
        }
    }

    private fun bitmapToFile(
            bitmap: Bitmap,
            fileNameToSave: String
    ): File? {
        var file: File? = null
        return try {
            file = File(
                    this.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
                            .toString() + File.separator + fileNameToSave
            )
            file.createNewFile()
            //Convert bitmap to byte array
            val bos = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 0, bos) // YOU can also save it in JPEG
            val bitMapData = bos.toByteArray()
            //write the bytes in file
            val fos = FileOutputStream(file)
            fos.write(bitMapData)
            fos.flush()
            fos.close()
            file
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
            file // it will return null
        }
    }
}