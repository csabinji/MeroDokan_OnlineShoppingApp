package com.sabin.onlineshoppingportal.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.sabin.onlineshoppingportal.OrderActivity
import com.sabin.onlineshoppingportal.ProductListActivity
import com.sabin.onlineshoppingportal.R
import com.sabin.onlineshoppingportal.UpdateProfileActivity
import com.sabin.onlineshoppingportal.api.ServiceBuilder
import com.sabin.onlineshoppingportal.repository.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AccountFragment : Fragment() {

    private lateinit var txtusername : TextView
    private lateinit var txtFname : TextView
    private lateinit var txtemail : TextView
    private lateinit var txtType : TextView
    private lateinit var imgProfile : ImageView
    private lateinit var btnEdit : Button
    private lateinit var btnOrder : Button
    private lateinit var btnProduct : Button

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
        imgProfile = view.findViewById(R.id.imgProfile)
        btnEdit = view.findViewById(R.id.btnEdit)
        btnOrder = view.findViewById(R.id.btnOrder)
        btnProduct = view.findViewById(R.id.btnProduct)

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
                        txtType.setText("${response.data?.accountType}")

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

        return view
    }
}