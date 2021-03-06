package com.sabin.onlineshoppingportal.adapter

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sabin.onlineshoppingportal.R
import com.sabin.onlineshoppingportal.api.ServiceBuilder
import com.sabin.onlineshoppingportal.entity.Cart
import com.sabin.onlineshoppingportal.entity.Order
import com.sabin.onlineshoppingportal.notification.NotificationChannels
import com.sabin.onlineshoppingportal.repository.CartRepository
import com.sabin.onlineshoppingportal.repository.OrderRepository
import com.sabin.onlineshoppingportal.repository.ProductRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CartAdapter(
    val lstCart: MutableList<Cart>,
    val context: Context
        ) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

            class CartViewHolder(view: View) : RecyclerView.ViewHolder(view){
                val imgProduct : ImageView
                val tvName : TextView
                val tvPrice : TextView
                val tvAddedBy : TextView
                val imgDelete : ImageView
                val btnOrder : Button
                init {
                    imgProduct = view.findViewById(R.id.imgProduct)
                    tvName = view.findViewById(R.id.tvName)
                    tvPrice = view.findViewById(R.id.tvPrice)
                    tvAddedBy = view.findViewById(R.id.tvAddedby)
                    imgDelete = view.findViewById(R.id.imgDelete)
                    btnOrder = view.findViewById(R.id.btnOrder)
                }
            }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.cart_layout,parent,false)
        return CartViewHolder(view)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
       val cart = lstCart[position]

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val productRepository = ProductRepository()
                val response = productRepository.getProduct(cart.product!!)
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
                    withContext(Main) {
                        notifyDataSetChanged()
                    }
                }

            }catch (ex: Exception){

            }
        }
        holder.tvAddedBy.text = cart.quantity.toString()

        holder.btnOrder.setOnClickListener {
            val quantity = cart.quantity.toString()

            val order = Order(quantity = quantity)

            CoroutineScope(Dispatchers.IO).launch {
                try{
                    val orderRepository = OrderRepository()
                    val response = orderRepository.addtoOrder(cart._id!!,order)
                    if(response.success==true){
                        showHighPriorityNotification()
                        withContext(Main){
                            Toast.makeText(context, "Order Placed", Toast.LENGTH_SHORT).show()
                            val cartRepository = CartRepository()
                            val response = cartRepository.deleteCart(cart._id!!)
                            if (response.success == true) {
                                withContext(Dispatchers.Main) {

                                }
                            }
                            withContext(Main) {
                                lstCart.remove(cart)
                                notifyDataSetChanged()
                            }
                        }
                    }
                }catch (ex: Exception){
                    Toast.makeText(context, ex.toString(), Toast.LENGTH_SHORT).show()
                }
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
                        val cartRepository = CartRepository()
                        val response = cartRepository.deleteCart(cart._id!!)
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
                        withContext(Main) {
                            lstCart.remove(cart)
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

    private fun showHighPriorityNotification() {

        val notificationManager = NotificationManagerCompat.from(context!!)

        val notificationChannels = NotificationChannels(context!!)
        notificationChannels.createNotificationChannels()

        val notification = NotificationCompat.Builder(context!!, notificationChannels.CHANNEL_1)
            .setSmallIcon(R.drawable.noti)
            .setContentTitle("Order Placed.")
            .setContentText("New product is added in the order list.")
            .setColor(Color.BLUE)
            .build()

        notificationManager.notify(1,notification)
    }

    override fun getItemCount(): Int {
        return lstCart.size
    }
}