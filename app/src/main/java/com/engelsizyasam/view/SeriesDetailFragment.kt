package com.engelsizyasam.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.engelsizyasam.R
import com.engelsizyasam.adapter.SeriesDetailAdapter
import com.engelsizyasam.adapter.SeriesDetailListener
import com.engelsizyasam.databinding.FragmentSeriesDetailBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class SeriesDetailFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding: FragmentSeriesDetailBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_series_detail, container, false)
        val application = requireNotNull(this.activity).application
        val args = SeriesDetailFragmentArgs.fromBundle(requireArguments())
        val viewModelFactory = SeriesDetailViewModelFactory(application, args.playlistId, args.seriesName)

        val viewModel = ViewModelProvider(this, viewModelFactory).get(SeriesDetailViewModel::class.java)

        binding.lifecycleOwner = this

        binding.viewModel = viewModel

        val navBar: BottomNavigationView = requireActivity().findViewById(R.id.bottomBar)
        navBar.visibility = View.INVISIBLE

        Log.e("id", args.playlistId)
        Log.e("name", args.seriesName)


        binding.backButton.setOnClickListener {
            it.findNavController().popBackStack()
        }

        val adapter = SeriesDetailAdapter(SeriesDetailListener { bookId ->
            viewModel.onLinkClicked(bookId)
        })


        viewModel.openVideoLink.observe(viewLifecycleOwner, {
            it?.let {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=$it")))
                viewModel.onLinkClickCompleted()
            }
        })

        binding.recyclerView.adapter = adapter

        viewModel.run()
        viewModel.properties.observe(viewLifecycleOwner, {
            adapter.data += it
            binding.progressBar.visibility = View.GONE

        })

        return binding.root
    }

    override fun onPause() {
        val navBar: BottomNavigationView = requireActivity().findViewById(R.id.bottomBar)
        navBar.visibility = View.VISIBLE
        super.onPause()
    }


}