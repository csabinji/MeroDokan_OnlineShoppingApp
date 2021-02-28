package com.sabin.onlineshoppingportal.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sabin.onlineshoppingportal.R
import com.sabin.onlineshoppingportal.adapter.ProductAdapter
import com.sabin.onlineshoppingportal.model.Product
import com.sabin.onlineshoppingportal.repository.ProductRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class HomeFragment : Fragment() {

    private var lstProduct = ArrayList<Product>()
    private lateinit var topRecycler : RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        topRecycler = view.findViewById(R.id.topRecycler)

        loadProduct()

        val adapter = ProductAdapter(lstProduct, context!!)
        val mLayoutManager = LinearLayoutManager(context)
        mLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        topRecycler.layoutManager = mLayoutManager
        topRecycler.adapter = adapter
        return view
    }
    private fun loadProduct() {
        lstProduct.add(Product(5,"Asus Predator","https://images-na.ssl-images-amazon.com/images/I/81g7AiqWrtL._AC_SL1500_.jpg","128000"))
        lstProduct.add(Product(1,"Unisex Blue Ray Glass","https://images-na.ssl-images-amazon.com/images/I/71eZwv%2B0qIL._AC_SL1500_.jpg","1200"))
        lstProduct.add(Product(2,"Mackie Studio Monitor","https://images-na.ssl-images-amazon.com/images/I/81SBInXziFL._AC_SL1500_.jpg","50000"))
        lstProduct.add(Product(3,"Rode Nt1A","https://sc1.musik-produktiv.com/pic-010018924xl/rode-nt1-a-complete-vocal-recording-solution.jpg","38000"))
        lstProduct.add(Product(4,"Acer Nitro 5","https://static.acer.com/up/Resource/Acer/Laptops/Nitro_5/Image/20191120/Nitro-5-AN515-43-modelpreview.png","108000"))

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val productRepository = ProductRepository()
                val response = productRepository.getAllProducts()
                if(response.success == true){
                    val lstStudent = response.data
                    withContext(Dispatchers.Main){
                        recyclerView.adapter = StudentAdapter(this@ViewStudentActivity, lstStudent!!)
                        recyclerView.layoutManager = LinearLayoutManager(this@ViewStudentActivity)
                    }
                }
            }catch (ex: Exception){
                withContext(Dispatchers.Main){
                    Toast.makeText(this@ViewStudentActivity, "Error : ${ex.toString()}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}