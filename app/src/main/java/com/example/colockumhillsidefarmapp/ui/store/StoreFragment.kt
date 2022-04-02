package com.example.colockumhillsidefarmapp.ui.store

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.colockumhillsidefarmapp.StoreProductRecViewAdapter
import com.example.colockumhillsidefarmapp.R
import com.example.colockumhillsidefarmapp.ShoppingCart
import com.example.colockumhillsidefarmapp.databinding.FragmentStoreBinding
import com.example.colockumhillsidefarmapp.ui.Storage


class StoreFragment : Fragment() {

    private var _binding: FragmentStoreBinding? = null
    private lateinit var productRecView: RecyclerView
    private lateinit var adapter: StoreProductRecViewAdapter

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val slideshowViewModel =
            ViewModelProvider(this).get(StoreViewModel::class.java)

        _binding = FragmentStoreBinding.inflate(inflater, container, false)
        val root: View = binding.root

        //val textView: TextView = binding.textStore
        //slideshowViewModel.text.observe(viewLifecycleOwner) {
        //    textView.text = it
        //}

        adapter = StoreProductRecViewAdapter(
            activity
        )
        productRecView = root.findViewById(R.id.productRecView)

        productRecView.adapter = adapter
        val manager : GridLayoutManager = GridLayoutManager(activity, 2)
        productRecView.layoutManager = manager

        adapter.setProducts(Storage.getInstance().allProducts)

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}