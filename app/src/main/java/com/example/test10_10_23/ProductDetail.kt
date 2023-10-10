package com.example.test10_10_23

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.test10_10_23.databinding.FragmentProductDetailBinding


class ProductDetail : Fragment() {
    private lateinit var binding: FragmentProductDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProductDetailBinding.bind(
            inflater.inflate(
                R.layout.fragment_product_detail,
                null
            )
        )
        initView()
        return binding.root

    }
    private fun initView(){
        val product  = requireArguments().getSerializable("product") as? Product

        if (product != null) {
            Glide.with(binding.root.context)
                .load(product.image)
                .into(binding.imageview)
            binding.txtTitle.text = "Product Title:${product.title}"
            binding.txtSubTitle.text = "Product SubTitle:${product.subtitle}"
            binding.txtisbn13.text= "Product isbn13:${product.isbn13}"
            binding.txtPrice.text= "Product Price:${product.price}"
        } else {
            // Handle the case where 'doctor' is null, for example, show an error message or return to the previous fragment.
        }
    }


}

