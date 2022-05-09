package com.engelsizyasam.presentation.series

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.engelsizyasam.R
import com.engelsizyasam.databinding.FragmentSeriesBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SeriesFragment : Fragment() {
    private lateinit var binding: FragmentSeriesBinding
    private lateinit var viewModel: SeriesViewModel
    private lateinit var adapter: SeriesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_series, container, false)
        viewModel = ViewModelProvider(this).get(SeriesViewModel::class.java)
        binding.lifecycleOwner = this

        viewModel.navigateToSeriesDetail.observe(viewLifecycleOwner) {
            it?.let {
                findNavController().navigate(SeriesFragmentDirections.actionSeriesFragmentToSeriesDetailFragment(it, viewModel.seriesName))
                viewModel.onSeriesDetailNavigated()
            }
        }


        adapter = SeriesAdapter(SeriesListener { playlistId, name ->
            viewModel.seriesName = name
            viewModel.onSeriesClicked(playlistId)

        })

        binding.recyclerView.adapter = adapter


        viewModel.properties.observe(viewLifecycleOwner) {
            adapter.data = it
            adapter.notifyDataSetChanged()
            //binding.progressBar.visibility = View.GONE
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