package com.engelsizyasam.presentation.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.engelsizyasam.R
import com.engelsizyasam.databinding.FragmentNewsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class NewsFragment : Fragment() {


    private val viewModel: NewsViewModel by viewModels()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentNewsBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_news, container, false)


        val organizationsAdapter = NewsAdapter(requireContext())
        val recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = organizationsAdapter

        lifecycleScope.launchWhenStarted {
            viewModel.uiState.collect { uiState ->
                when {
                    uiState.news.isNotEmpty() -> {
                        organizationsAdapter.data = uiState.news
                    }
                    uiState.isLoading -> {}
                    uiState.error.isNotEmpty() -> {}
                }
            }
        }

        return binding.root
    }

}