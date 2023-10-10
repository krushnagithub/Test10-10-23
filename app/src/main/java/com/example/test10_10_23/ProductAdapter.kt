package com.example.test10_10_23

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.test10_10_23.databinding.ProductviewBinding

class ProductAdapter(private val productList: ArrayList<Product>) :
    RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    interface ProductDetailOnClickListener {
        fun onDetailClick(product: Product, adapterPosition: Int)
    }

    var productDetailOnClickListener: ProductDetailOnClickListener? = null

    inner class ProductViewHolder(private val binding: ProductviewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                productDetailOnClickListener?.onDetailClick(productList[adapterPosition],
                    adapterPosition)
            }
        }

        fun bind(product: Product) {
            Glide.with(binding.root.context)
                .load(product.image)
                .into(binding.imageview)
            binding.txtTitle.text = product.title
            binding.txtSubTitle.text = product.subtitle
            binding.txtisbn13.text = product.isbn13
            binding.txtPrice.text = product.price
        }
    }

    override fun getItemCount() = productList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = ProductviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = productList[position]
        holder.bind(product)
    }
}
