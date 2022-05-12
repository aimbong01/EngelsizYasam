package com.engelsizyasam.presentation.seriesdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.engelsizyasam.R
import com.engelsizyasam.databinding.FragmentSeriesDetailBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class SeriesDetailFragment : Fragment() {

    private val viewModel: SeriesDetailViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding = FragmentSeriesDetailBinding.inflate(inflater)

        val navBar: BottomNavigationView = requireActivity().findViewById(R.id.bottomBar)
        navBar.visibility = View.INVISIBLE

        binding.backButton.setOnClickListener {
            it.findNavController().popBackStack()
        }

        binding.textView.text = viewModel.seriesName


        val adapter = SeriesDetailAdapter(requireContext())
        val recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = adapter

        lifecycleScope.launchWhenStarted {
            viewModel.uiState.collect { uiState ->
                when {
                    uiState.seriesDetail.isNotEmpty() -> {
                        binding.progressBar.visibility = View.GONE
                        adapter.data = uiState.seriesDetail
                    }
                    uiState.isLoading -> {

                    }
                    uiState.error.isNotEmpty() -> {

                    }
                }
            }
        }

        return binding.root
    }

    override fun onPause() {
        val navBar: BottomNavigationView = requireActivity().findViewById(R.id.bottomBar)
        navBar.visibility = View.VISIBLE
        super.onPause()
    }


}