package com.sabin.onlineshoppingportal.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sabin.onlineshoppingportal.R
import com.sabin.onlineshoppingportal.api.ServiceBuilder
import com.sabin.onlineshoppingportal.entity.Product

class ProductAdapter (
    val lstProduct : MutableList<Product>,
    val context: Context
) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>(){

    class ProductViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val imgProduct : ImageView
        val tvName : TextView
        val tvPrice :TextView
        init {
            imgProduct = view.findViewById(R.id.imgProduct1)
            tvName = view.findViewById(R.id.tvName)
            tvPrice = view.findViewById(R.id.tvPrice)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.product_layout,parent,false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = lstProduct[position]
        holder.tvName.text = product.name
        holder.tvPrice.text = product.price

        val imagePath = ServiceBuilder.loadImagePath() + product.image
        if(!product.image.equals("upload.jpg")) {
            Glide.with(context)
                    .load(imagePath)
                    .fitCenter()
                    .into(holder.imgProduct)
        }
    }
    override fun getItemCount(): Int {
        return lstProduct.size
    }
}