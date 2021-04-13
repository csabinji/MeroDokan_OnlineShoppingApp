package com.sabin.onlineshoppingportal.adapter

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sabin.onlineshoppingportal.R
import com.sabin.onlineshoppingportal.api.ServiceBuilder
import com.sabin.onlineshoppingportal.entity.Order
import com.sabin.onlineshoppingportal.repository.CartRepository
import com.sabin.onlineshoppingportal.repository.OrderRepository
import com.sabin.onlineshoppingportal.repository.ProductRepository
import com.sabin.onlineshoppingportal.repository.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class OrderAdapter (
        val lstOrder : MutableList<Order>,
        val context: Context
        ) : RecyclerView.Adapter<OrderAdapter.OrderViewHolder>() {

    class OrderViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val imgProduct : ImageView
        val tvName : TextView
        val tvPrice : TextView
        val tvAddedBy : TextView
        val imgDelete : ImageView
        init {
            imgProduct = view.findViewById(R.id.imgProduct)
            tvName = view.findViewById(R.id.tvName)
            tvPrice = view.findViewById(R.id.tvPrice)
            tvAddedBy = view.findViewById(R.id.tvAddedby)
            imgDelete = view.findViewById(R.id.imgDelete)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.order_layout,parent,false)
        return OrderViewHolder(view)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val order = lstOrder[position]

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val productRepository = ProductRepository()
                val response = productRepository.getProduct(order.product!!)
                if(response.success==true){
                    val product = response.data!!
                    withContext(Dispatchers.Main){
                        holder.tvName.text = product?.name
                        holder.tvPrice.text = "Rs." + product?.price

                        val imagePath = ServiceBuilder.loadImagePath() + product.image
                        if(!product.image.equals("upload.jpg")) {
                            Glide.with(context)
                                .load(imagePath)
                                .fitCenter()
                                .into(holder.imgProduct)
                        }
                    }

                    val userRepository = UserRepository()
                    val response = userRepository.getBuyer(order.bname!!)
                    if(response.success == true){
                        val user = response.data!!
                        withContext(Dispatchers.Main){
                            holder.tvAddedBy.text = user?.username
                        }
                    }
                    withContext(Dispatchers.Main) {
                        notifyDataSetChanged()
                    }
                }

            }catch (ex: Exception){

            }
        }

        holder.imgDelete.setOnClickListener{
            val builder = AlertDialog.Builder(context)
            builder.setTitle("Delete Product")
            builder.setMessage("Are you sure want to delete ?")
            builder.setIcon(android.R.drawable.ic_dialog_alert)
            builder.setPositiveButton("Yes") { _, _ ->
                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        val orderRepository = OrderRepository()
                        val response = orderRepository.deleteOrder(order._id!!)
                        if (response.success == true) {
                            withContext(Dispatchers.Main) {
                                Toast.makeText(
                                    context,
                                    "Product Deleted",
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                            }
                        }
                        withContext(Dispatchers.Main) {
                            lstOrder.remove(order)
                            notifyDataSetChanged()
                        }

                    } catch (ex: Exception) {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(context, ex.toString(), Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
            builder.setNegativeButton("No"){_, _ ->
                Toast.makeText(context, "Action Cancelled", Toast.LENGTH_SHORT).show()
            }
            val alertDialog: AlertDialog = builder.create()
            alertDialog.setCancelable(false)
            alertDialog.show()

        }
    }

    override fun getItemCount(): Int {
        return lstOrder.size
    }
}