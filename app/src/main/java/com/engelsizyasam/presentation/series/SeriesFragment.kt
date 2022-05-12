package com.engelsizyasam.presentation.series

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.engelsizyasam.databinding.FragmentSeriesBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class SeriesFragment : Fragment() {
    private val viewModel: SeriesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding = FragmentSeriesBinding.inflate(inflater)

        val adapter = SeriesAdapter()
        val recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = adapter

        lifecycleScope.launchWhenStarted {
            viewModel.uiState.collect { uiState ->
                when {
                    uiState.series.isNotEmpty() -> {
                        adapter.data = uiState.series
                    }
                    uiState.isLoading -> {}
                    uiState.error.isNotEmpty() -> {}
                }
            }
        }

        binding.search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                adapter.getFilter().filter(p0)
                return true
            }

            override fun onQueryTextChange(query: String): Boolean {
                adapter.getFilter().filter(query)
                return true
            }
        })


        return binding.root
    }
}