package com.engelsizyasam.ui

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.engelsizyasam.R
import com.engelsizyasam.adapter.ScholarAdapter
import com.engelsizyasam.adapter.ScholarListener
import com.engelsizyasam.adapter.SeriesDetailAdapter
import com.engelsizyasam.adapter.SeriesDetailListener
import com.engelsizyasam.databinding.FragmentScholarBinding
import com.engelsizyasam.viewmodel.ScholarViewModel

class ScholarFragment : Fragment() {

    private lateinit var viewModel: ScholarViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentScholarBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_scholar, container, false)
        viewModel = ViewModelProvider(this).get(ScholarViewModel::class.java)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        val adapter = ScholarAdapter(ScholarListener {
            viewModel.onLinkClicked(it)
        })

        viewModel.openLink.observe(viewLifecycleOwner, {
            it?.let {
                findNavController().navigate(ScholarFragmentDirections.actionScholarFragmentToScholarDetailFragment(it))
                viewModel.onLinkClickCompleted()
            }
        })

        binding.recyclerView.adapter = adapter
        viewModel.run()

        viewModel.properties.observe(viewLifecycleOwner, {
            adapter.data += it
        })


        return binding.root
    }

}