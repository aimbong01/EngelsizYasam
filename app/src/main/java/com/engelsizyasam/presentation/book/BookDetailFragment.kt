package com.engelsizyasam.presentation.book

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.engelsizyasam.BaseApplication
import com.engelsizyasam.R
import com.engelsizyasam.databinding.FragmentBookDetailBinding
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class BookDetailFragment : Fragment(), OnPageChangeListener {

    var pageNumber = 0
    var bookId = 0

    lateinit var viewModel: BookDetailViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding: FragmentBookDetailBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_book_detail, container, false)

        //val application = requireNotNull(this.activity).application
        //val arguments: BookDetailFragmentArgs by navArgs()
        //val dataSource = BookDatabase.getInstance(application).bookDatabaseDao
        //val viewModelFactory = BookDetailViewModelFactory(arguments.bookId, dataSource)
        viewModel = ViewModelProvider(this).get(BookDetailViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this


        viewModel.getBook().observe(viewLifecycleOwner) {
            bookId = it.bookId
            binding.PDFView.fromAsset(it.bookPDF)
                .defaultPage(it.bookPage)
                .onPageChange(this)
                .enableAnnotationRendering(true)
                .scrollHandle(DefaultScrollHandle(requireContext()))
                .load()
        }

        val navBar: BottomNavigationView = requireActivity().findViewById(R.id.bottomBar)
        navBar.visibility = View.INVISIBLE

        binding.backButton.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {
                //viewModel.updatePage(bookId, pageNumber)
            }
            it.findNavController().popBackStack()
        }

        return binding.root
    }

    override fun onPause() {
        viewLifecycleOwner.lifecycleScope.launch {
            //viewModel.updatePage(bookId, pageNumber)
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