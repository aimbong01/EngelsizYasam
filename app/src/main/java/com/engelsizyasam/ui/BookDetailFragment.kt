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
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle
import com.google.android.material.bottomnavigation.BottomNavigationView
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.engelsizyasam.model.BookModel
import kotlinx.coroutines.launch

class BookDetailFragment : Fragment(), OnPageChangeListener {

    var pageNumber = 0
    var bookId = 0

    lateinit var viewModel:BookDetailViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding: FragmentBookDetailBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_book_detail, container, false)
        val application = requireNotNull(this.activity).application
        val arguments: BookDetailFragmentArgs by navArgs()
        val dataSource = BookDatabase.getInstance(application).bookDatabaseDao
        val viewModelFactory = BookDetailViewModelFactory(arguments.bookId, dataSource)
        viewModel = ViewModelProvider(this, viewModelFactory).get(BookDetailViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this


        viewModel.getBook().observe(viewLifecycleOwner, {
            bookId = it.bookId
            binding.PDFView.fromAsset(it.bookPDF)
                .defaultPage(it.bookPage)
                .onPageChange(this)
                .enableAnnotationRendering(true)
                .scrollHandle(DefaultScrollHandle(application))
                .load()
        })

        val navBar: BottomNavigationView = requireActivity().findViewById(R.id.bottomBar)
        navBar.visibility = View.INVISIBLE

        binding.backButton.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.updatePage(bookId, pageNumber)
            }
            it.findNavController().popBackStack()
        }

        return binding.root
    }

    override fun onPause() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.updatePage(bookId, pageNumber)
        }

        val navBar: BottomNavigationView = requireActivity().findViewById(R.id.bottomBar)
        navBar.visibility = View.VISIBLE
        super.onPause()
    }

    override fun onPageChanged(page: Int, pageCount: Int) {
        pageNumber = page
        Log.d("page", page.toString())
    }

}