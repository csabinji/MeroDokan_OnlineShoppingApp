package com.sabin.onlineshoppingportal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.bumptech.glide.Glide
import com.sabin.onlineshoppingportal.api.ServiceBuilder
import com.sabin.onlineshoppingportal.entity.Product
import com.sabin.onlineshoppingportal.repository.ProductRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UpdateProductActivity : AppCompatActivity() {

    private lateinit var etxtPname : EditText
    private lateinit var etxtPrice : EditText
    private lateinit var etxtDes : EditText
    private lateinit var imgProduct : ImageView
    private lateinit var txtCategory : TextView
    private lateinit var btnUpdate : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_product)

        etxtPname = findViewById(R.id.etxtPname)
        etxtPrice = findViewById(R.id.etxtPrice)
        etxtDes = findViewById(R.id.etxtDes)
        imgProduct = findViewById(R.id.imgProduct)
        txtCategory = findViewById(R.id.txtCategory)
        btnUpdate = findViewById(R.id.btnUpdate)

        val product = intent.getParcelableExtra<Product>("product")!!
        etxtPname.setText(product.name)
        etxtPrice.setText(product.price)
        etxtDes.setText(product.dec)
        txtCategory.setText(product.category)

        val image = ServiceBuilder.loadImagePath() + product.image

        Glide.with(this)
            .load(image)
            .fitCenter()
            .into(imgProduct)

        btnUpdate.setOnClickListener {
            val name = etxtPname.text.toString()
            val price = etxtPrice.text.toString()
            val dec = etxtDes.text.toString()

            val product2 = Product(_id = product._id,name = name, price = price, dec = dec)

            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val productRepository = ProductRepository()
                    val response = productRepository.updateProduct(product2)
                    if(response.success == true){
                        withContext(Dispatchers.Main) {
                            Toast.makeText(
                                this@UpdateProductActivity,
                                response.success.toString(),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }catch (ex: Exception){
                    Toast.makeText(this@UpdateProductActivity, ex.toString(), Toast.LENGTH_SHORT).show()
                }
            }
        }
        }
    }