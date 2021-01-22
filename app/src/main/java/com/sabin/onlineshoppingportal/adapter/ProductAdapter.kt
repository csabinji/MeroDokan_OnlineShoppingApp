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
import com.sabin.onlineshoppingportal.model.Product

class ProductAdapter (
    val lstProduct : ArrayList<Product>,
    val context: Context
) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>(){

    class ProductViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val imgProduct : ImageView
        val tvName : TextView
        val tvPrice :TextView
        init {
            imgProduct = view.findViewById(R.id.imgProduct)
            tvName = view.findViewById(R.id.tvName)
            tvPrice = view.findViewById(R.id.tvPrice)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.product_layout,parent,false)
        return ProductViewHolder(view)
    }

    override fun getItemCount(): Int {
        return lstProduct.size
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = lstProduct[position]
        holder.tvName.text = product.ProductName
        holder.tvPrice.text = product.ProductPrice

        Glide.with(context)
            .load(product.ProductImage)
            .into(holder.imgProduct)
    }
}