package com.engelsizyasam.ui

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.engelsizyasam.R
import com.engelsizyasam.database.BookDatabase
import com.engelsizyasam.databinding.BookDetailFragmentBinding
import com.engelsizyasam.viewmodel.BookDetailViewModel
import com.engelsizyasam.viewmodel.BookDetailViewModelFactory
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle

class BookDetailFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding: BookDetailFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.book_detail_fragment, container, false)
        val application = requireNotNull(this.activity).application
        val arguments: BookDetailFragmentArgs by navArgs()
        val dataSource = BookDatabase.getInstance(application).bookDatabaseDao

        val viewModelFactory = BookDetailViewModelFactory(arguments.bookId, dataSource)

        val viewModel = ViewModelProvider(this, viewModelFactory).get(BookDetailViewModel::class.java)
        binding.viewModel = viewModel

        binding.setLifecycleOwner(this)

        viewModel.getBook().observe(viewLifecycleOwner, {
            binding.PDFView.fromAsset(it.bookPDF)
                .scrollHandle(DefaultScrollHandle(application))
                .defaultPage(0)
                .load()
        })

        return binding.root
    }

}