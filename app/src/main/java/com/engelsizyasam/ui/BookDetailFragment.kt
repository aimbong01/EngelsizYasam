package com.engelsizyasam.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.engelsizyasam.R
import com.engelsizyasam.database.BookDatabase
import com.engelsizyasam.databinding.FragmentBookDetailBinding
import com.engelsizyasam.viewmodel.BookDetailViewModel
import com.engelsizyasam.viewmodel.BookDetailViewModelFactory
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle
import com.google.android.material.bottomnavigation.BottomNavigationView

class BookDetailFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding: FragmentBookDetailBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_book_detail, container, false)
        val application = requireNotNull(this.activity).application
        val arguments: BookDetailFragmentArgs by navArgs()
        val dataSource = BookDatabase.getInstance(application).bookDatabaseDao

        val viewModelFactory = BookDetailViewModelFactory(arguments.bookId, dataSource)

        val viewModel = ViewModelProvider(this, viewModelFactory).get(BookDetailViewModel::class.java)
        binding.viewModel = viewModel

        binding.lifecycleOwner = this



        viewModel.getBook().observe(viewLifecycleOwner, {
            binding.PDFView.fromAsset(it.bookPDF)
                .defaultPage(0)
                .scrollHandle(DefaultScrollHandle(application))
                .load()
        })

        val navBar: BottomNavigationView = requireActivity().findViewById(R.id.bottomBar)
        navBar.visibility = View.INVISIBLE

        binding.backButton.setOnClickListener {
            it.findNavController().popBackStack()
        }

        return binding.root
    }

    override fun onPause() {
        val navBar: BottomNavigationView = requireActivity().findViewById(R.id.bottomBar)
        navBar.visibility = View.VISIBLE
        super.onPause()
    }

}