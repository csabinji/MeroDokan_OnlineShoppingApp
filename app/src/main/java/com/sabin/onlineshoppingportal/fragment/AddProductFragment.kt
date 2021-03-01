package com.sabin.onlineshoppingportal.fragment

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.sabin.onlineshoppingportal.R
import com.sabin.onlineshoppingportal.db.ProductDB
import com.sabin.onlineshoppingportal.entity.Product
import com.sabin.onlineshoppingportal.repository.ProductRepository
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

class AddProductFragment : Fragment() {

    private lateinit var etxtPname : EditText
    private lateinit var etxtPdec : EditText
    private lateinit var etxtPrice : EditText
    private lateinit var imgProduct : ImageView
    private lateinit var btnSave : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add_product2, container, false)

        etxtPname = view.findViewById(R.id.etxtPname)
        etxtPdec = view.findViewById(R.id.etxtPdec)
        etxtPrice = view.findViewById(R.id.etxtPrice)
        imgProduct = view.findViewById(R.id.imgProduct)
        btnSave = view.findViewById(R.id.btnSave)

        btnSave.setOnClickListener {
            uploadProduct()
        }

        imgProduct.setOnClickListener {
            loadPopUpMenu()
        }

        return view
    }
    private fun uploadProduct() {

        val productName = etxtPname.text.toString()
        val productDec = etxtPdec.text.toString()
        val productPrice = etxtPrice.text.toString()

        val product = Product(name = productName, dec = productDec, price = productPrice)

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val productRepository = ProductRepository()
                val response = productRepository.addProduct(product)
                if (response.success == true) {
                    ProductDB.getInstance(context!!).getProductDAO().insertProduct(product)
                    if(imageUrl != null){
                        withContext(Dispatchers.Main) {
                            Toast.makeText(
                                    context, imageUrl.toString(),
                                    Toast.LENGTH_SHORT
                            ).show()
                        }

                        uploadImage(response.data!!._id!!)
                    }
                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                            context, response.message.toString(),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            } catch (ex: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(getActivity(), ex.toString(), Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

    private fun uploadImage(productId: String) {
        if (imageUrl != null) {
            val file = File(imageUrl!!)
            val reqFile =
                RequestBody.create(MediaType.parse("multipart/form-data"), file)
            val body =
                MultipartBody.Part.createFormData("file", file.name, reqFile)
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val productRepository = ProductRepository()
                    val response = productRepository.uploadImage(productId, body)
                    if (response.success == true) {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(getActivity(), response.message.toString(), Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                    else{
                        withContext(Dispatchers.Main) {
                            Toast.makeText(getActivity(), response.message.toString(), Toast.LENGTH_SHORT)
                                    .show()
                        }
                    }
                } catch (ex: Exception) {
                    withContext(Dispatchers.Main) {
                        Log.d("Mero Error ", ex.localizedMessage)
                        Toast.makeText(
                            getActivity(),
                            ex.localizedMessage,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }

    }

    private fun loadPopUpMenu() {
        val popupMenu = PopupMenu(getActivity(), imgProduct)
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
                val contentResolver = context?.contentResolver
                val cursor =
                        contentResolver?.query(selectedImage!!, filePathColumn, null, null, null)
                cursor!!.moveToFirst()
                val columnIndex = cursor.getColumnIndex(filePathColumn[0])
                imageUrl = cursor.getString(columnIndex)
                imgProduct.setImageBitmap(BitmapFactory.decodeFile(imageUrl))
                cursor.close()
            } else if (requestCode == REQUEST_CAMERA_CODE && data != null) {
                val imageBitmap = data.extras?.get("data") as Bitmap
                val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
                val file = bitmapToFile(imageBitmap, "$timeStamp.jpg")
                imageUrl = file!!.absolutePath
                imgProduct.setImageBitmap(BitmapFactory.decodeFile(imageUrl))
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
                getActivity()?.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
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