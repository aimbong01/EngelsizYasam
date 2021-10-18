package com.engelsizyasam.view

import android.app.Application
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.engelsizyasam.R
import com.engelsizyasam.adapter.SeriesAdapter
import com.engelsizyasam.adapter.SeriesListener
import com.engelsizyasam.databinding.FragmentSeriesBinding

class SeriesFragment : Fragment() {
    private lateinit var binding: FragmentSeriesBinding
    private lateinit var application: Application
    private lateinit var viewModelFactory: SeriesViewModelFactory
    private lateinit var viewModel: SeriesViewModel
    private lateinit var adapter: SeriesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_series, container, false)
        application = requireNotNull(this.activity).application
        viewModelFactory = SeriesViewModelFactory(application)
        viewModel = ViewModelProvider(this, viewModelFactory).get(SeriesViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        viewModel.navigateToSeriesDetail.observe(viewLifecycleOwner, {
            it?.let {
                findNavController().navigate(SeriesFragmentDirections.actionSeriesFragmentToSeriesDetailFragment(it, viewModel.seriesName))
                viewModel.onSeriesDetailNavigated()
            }
        })


        adapter = SeriesAdapter(application, SeriesListener { playlistId, name ->
            viewModel.seriesName = name
            viewModel.onSeriesClicked(playlistId)

        })

        binding.recyclerView.adapter = adapter

        viewModel.run()
        viewModel.properties.observe(viewLifecycleOwner, {
            adapter.data += it
            //binding.progressBar.visibility = View.GONE
        })


        return binding.root
    }
}