package com.engelsizyasam.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.engelsizyasam.BaseApplication
import com.engelsizyasam.R
import com.engelsizyasam.adapter.NewsAdapter
import com.engelsizyasam.databinding.FragmentNewsBinding
import com.engelsizyasam.databinding.FragmentScholarBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class NewsFragment : Fragment() {

    private lateinit var viewModel: NewsViewModel
    private lateinit var adapter: NewsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentNewsBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_news, container, false)
        viewModel = ViewModelProvider(this).get(NewsViewModel::class.java)

        binding.lifecycleOwner = this

        adapter = NewsAdapter(container?.context!!)

        binding.recyclerView.adapter = adapter

        viewModel.run()
        viewModel.properties.observe(viewLifecycleOwner, {
            adapter.data = it
        })

        return binding.root
    }

}