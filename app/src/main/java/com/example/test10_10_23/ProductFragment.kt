package com.example.test10_10_23

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.test10_10_23.databinding.FragmentProductBinding
import org.json.JSONException


class ProductFragment : Fragment() {
    private lateinit var binding:FragmentProductBinding
    private lateinit var productList:ArrayList<Product>
    private lateinit var productadapter:ProductAdapter
    private lateinit var requestQueue: RequestQueue



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentProductBinding.bind(inflater.inflate(R.layout.fragment_product,null))
        productList = ArrayList()
        initView()
        requestQueue = Volley.newRequestQueue(requireActivity())
        fetchData()
        setUpListners()
        return binding.root
    }
    private fun setUpListners(){
        productadapter.productDetailOnClickListener =
            object : ProductAdapter.ProductDetailOnClickListener {
                override fun onDetailClick(product: Product, adapterPosition: Int) {
                    val productDetailFragment = ProductDetail()

                    val bundle = Bundle()
                    bundle.putSerializable("product",product)
                    productDetailFragment.arguments = bundle

                    parentFragmentManager.beginTransaction()
                        .replace(R.id.container, productDetailFragment)
                        .addToBackStack(null)
                        .commit()
                    mt("${product.title} clicked")
                }


            }
    }
    private fun fetchData(){
        val url = "https://api.itbook.store/1.0/new"

        val request = JsonObjectRequest(
            Request.Method.GET, url, null,
            Response.Listener { jsonObject ->
                try {
                    val articlesArray = jsonObject.getJSONArray("books")

                    val newProductList= ArrayList<Product>()

                    for (i in 0 until articlesArray.length()) {
                        val articleObject = articlesArray.getJSONObject(i)
                        val product = Product(
                            articleObject.optString("title", "No Title Available"),
                            articleObject.optString("subtitle", "No Description Available"),
                            articleObject.optString("isbn13", "No Recommendation Available"),
                            articleObject.optString("price", "No Solution Available"),
                            articleObject.optString("image", "")
                        )

                        newProductList.add(product)
                    }

                    productList.clear()
                    productList.addAll(newProductList)

                    productadapter.notifyDataSetChanged()
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            Response.ErrorListener { error: VolleyError? ->
                error?.printStackTrace()
            })

        requestQueue.add(request)
    }

    private fun initView(){
  binding.RecyclerView.layoutManager=LinearLayoutManager(activity,LinearLayoutManager.VERTICAL,false)
        productadapter= ProductAdapter(productList)
        binding.RecyclerView.adapter=productadapter
    }
    private fun mt(text:String){
        Toast.makeText(activity,text,Toast.LENGTH_SHORT).show()
    }
}