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
import com.sabin.onlineshoppingportal.db.ProductDB
import com.sabin.onlineshoppingportal.entity.Product
import com.sabin.onlineshoppingportal.repository.ProductRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class HomeFragment : Fragment() {

    private lateinit var topRecycler : RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        topRecycler = view.findViewById(R.id.topRecycler)
        loadProduct()
        getProduct()
        return view
    }

    private fun getProduct(){
        try{
            CoroutineScope(Dispatchers.IO).launch {
                val lstProduct = ProductDB.getInstance(context!!).getProductDAO().getAllProduct()
                withContext(Dispatchers.Main){
                    val mLayoutManager = LinearLayoutManager(context)
                    mLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
                    topRecycler.layoutManager = mLayoutManager
                    topRecycler.adapter = ProductAdapter(lstProduct, context!!)
                }
            }
        }catch (ex: Exception){
            }
        }

    private fun loadProduct(){
        try{
            CoroutineScope(Dispatchers.IO).launch {
                ProductDB.getInstance(context!!).getProductDAO().deleteAll()
                withContext(Dispatchers.Main){
                    val productRepository = ProductRepository()
                    val response = productRepository.getAllProducts()
                    response.data!!.forEach{
                    ProductDB.getInstance(context!!).getProductDAO().insertProduct(it)
                }
                }
            }
        }catch (ex: Exception){

        }
    }


//    private fun loadProduct() {
//
//        CoroutineScope(Dispatchers.IO).launch {
//            try {
//                val productRepository = ProductRepository()
//                val response = productRepository.getAllProducts()
//                response.data!!.forEach{
//                    ProductDB.getInstance(context!!).getProductDAO().insertProduct(it)
//                }
//                if(response.success == true){
//                        val lstProduct = response.data!!
//                        val mLayoutManager = LinearLayoutManager(context)
//                        mLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
//                        topRecycler.layoutManager = mLayoutManager
//                        topRecycler.adapter = ProductAdapter(lstProduct, context!!)
//
//                }
//            }catch (ex: Exception){
//                withContext(Dispatchers.Main){
//                    Toast.makeText(getActivity(), "Error : ${ex.toString()}", Toast.LENGTH_SHORT).show()
//                }
//            }
//        }
//    }
}