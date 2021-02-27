package com.sabin.onlineshoppingportal.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.sabin.onlineshoppingportal.R
import com.sabin.onlineshoppingportal.entity.Product
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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
                val studentRepository = StudentRepository()
                val response = studentRepository.addStudent(student)
                if (response.success == true) {
                    if(imageUrl != null){
                        uploadImage(response.data!!._id!!)
                    }
                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                            this@AddStudentActivity,
                            "Registered Successful",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            } catch (ex: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@AddStudentActivity, ex.toString(), Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }
}