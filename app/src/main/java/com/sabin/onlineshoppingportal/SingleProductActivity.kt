package com.sabin.onlineshoppingportal

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.sabin.onlineshoppingportal.adapter.User
import com.sabin.onlineshoppingportal.api.ServiceBuilder
import com.sabin.onlineshoppingportal.entity.Cart
import com.sabin.onlineshoppingportal.entity.Product
import com.sabin.onlineshoppingportal.repository.CartRepository
import com.sabin.onlineshoppingportal.repository.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SingleProductActivity : AppCompatActivity() {

    private lateinit var imgProduct : ImageView
    private lateinit var txtPname : TextView
    private lateinit var txtCategory : TextView
    private lateinit var txtPrice : TextView
    private lateinit var txtDes : TextView
    private lateinit var txtQty : TextView
    private lateinit var btnCart : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_product)

        imgProduct = findViewById(R.id.imgProduct)
        txtPname = findViewById(R.id.txtPname)
        txtCategory = findViewById(R.id.txtCategory)
        txtPrice = findViewById(R.id.txtPrice)
        txtDes = findViewById(R.id.txtDes)
        txtQty = findViewById(R.id.txtQty)
        btnCart = findViewById(R.id.btnCart)

        val product = intent.getParcelableExtra<Product>("product")!!
        txtPname.setText(product.name)
        txtCategory.setText(product.category)
        txtPrice.setText("Rs." + product.price)
        txtDes.setText(product.dec)
        txtQty.setText("1")

        val image = ServiceBuilder.loadImagePath() + product.image

        Glide.with(this)
                .load(image)
                .fitCenter()
                .into(imgProduct)

        btnCart.setOnClickListener {
            val quantity = txtQty.text.toString()

            val cart = Cart(quantity = quantity)

            if(ServiceBuilder.accountType=="Buyer") {

                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        val cartRepository = CartRepository()
                        val response = cartRepository.addtoCart(product._id!!, cart)
                        if (response.success == true) {
                            withContext(Main) {
                                Toast.makeText(this@SingleProductActivity, "Added", Toast.LENGTH_SHORT).show()
                            }
                        }

                    } catch (ex: Exception) {

                    }
                }
            }
            else{
                Toast.makeText(this@SingleProductActivity, "Seller can't add product to Cart", Toast.LENGTH_SHORT).show()
            }
        }
    }
}