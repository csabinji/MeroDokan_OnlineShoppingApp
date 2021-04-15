package com.sabin.onlineshoppingportal.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sabin.onlineshoppingportal.GoogleMapActivity
import com.sabin.onlineshoppingportal.R
import com.sabin.onlineshoppingportal.UpdateProfileActivity
import com.sabin.onlineshoppingportal.adapter.ProductAdapter
import com.sabin.onlineshoppingportal.db.ProductDB
import com.sabin.onlineshoppingportal.entity.Product
import com.sabin.onlineshoppingportal.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking

class HomeFragment : Fragment() {

    private lateinit var topRecycler : RecyclerView
    private var products = mutableListOf<Product>()
    private lateinit var imgIcon : ImageView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        topRecycler = view.findViewById(R.id.topRecycler)

        imgIcon = view.findViewById(R.id.imgIcon)

        imgIcon.setOnClickListener {
            val intent = Intent(activity, GoogleMapActivity::class.java)
            startActivity(intent)
        }

        runBlocking {
            deleteProducts().collect()
            loadProduct().collect()
            getProduct().collect{value -> products = value.toMutableList().asReversed() }
        }
        val adapter = ProductAdapter(products, requireContext())
        topRecycler.adapter = adapter
        topRecycler.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        return view
    }

    private fun deleteProducts(): Flow<String> = flow{
        ProductDB.getInstance(requireContext()).getProductDAO().deleteAll()
        emit("Done")
    }

    private fun loadProduct(): Flow<String> = flow{
        val productRepository = ProductRepository()
        val response = productRepository.getAllProducts()
        response.data!!.forEach {
            ProductDB.getInstance(requireContext()).getProductDAO().insertProduct(it)
            emit("Done")
        }
    }

    private fun getProduct() : Flow<List<Product>> = flow {
        val listProducts = ProductDB.getInstance(requireContext()).getProductDAO().getAllProduct()
        emit(listProducts)
    }
}