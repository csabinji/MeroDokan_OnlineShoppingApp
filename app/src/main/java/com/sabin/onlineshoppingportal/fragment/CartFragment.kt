package com.sabin.onlineshoppingportal.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sabin.onlineshoppingportal.R
import com.sabin.onlineshoppingportal.adapter.CartAdapter
import com.sabin.onlineshoppingportal.adapter.ProductAdapter
import com.sabin.onlineshoppingportal.entity.Cart
import com.sabin.onlineshoppingportal.entity.Product
import com.sabin.onlineshoppingportal.repository.CartRepository
import com.sabin.onlineshoppingportal.repository.ProductRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CartFragment : Fragment() {

    private lateinit var topRecycler : RecyclerView
    private var lstCart = mutableListOf<Cart>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_cart, container, false)

        topRecycler = view.findViewById(R.id.topRecycler)

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val cartRepository = CartRepository()
                val response = cartRepository.getCart()
                withContext(Dispatchers.Main) {
                    Toast.makeText(getActivity(),response.success.toString(), Toast.LENGTH_SHORT)
                            .show()
                }
                if (response.success == true) {
                    lstCart = response.data!!
                    withContext(Dispatchers.Main) {
                        topRecycler.adapter = CartAdapter(lstCart, context!!)
                        topRecycler.layoutManager = LinearLayoutManager(context)
                    }
                }
                else{
                    withContext(Dispatchers.Main) {
                        Toast.makeText(getActivity(),"", Toast.LENGTH_SHORT)
                                .show()
                    }
                }
            } catch (ex: Exception) {
                withContext(Dispatchers.Main) {
                    Log.d("Error", ex.localizedMessage)
                    Toast.makeText(
                            context!!,
                            ex.localizedMessage,
                            Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

        return view
    }

}