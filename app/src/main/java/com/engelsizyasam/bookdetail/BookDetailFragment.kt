package com.engelsizyasam.bookdetail

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.engelsizyasam.R
import com.engelsizyasam.databinding.BookDetailFragmentBinding
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle

class BookDetailFragment : Fragment() {

    lateinit var mcontext: Context

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mcontext = context

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding: BookDetailFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.book_detail_fragment, container, false)
        val arguments = BookDetailFragmentArgs.fromBundle(arguments!!)
        val viewModelFactory = BookDetailViewModelFactory(arguments.bookId, mcontext)

        val viewModel = ViewModelProvider(this, viewModelFactory).get(BookDetailViewModel::class.java)
        binding.viewModel = viewModel

        binding.setLifecycleOwner(this)

        /*var site = "https://metaldolap.com.tr/uploads/site/katalog.pdf"
        var uri: Uri = Uri.parse(site)*/
        binding.PDFView.fromAsset("${viewModel.pdfName}")
            .scrollHandle(DefaultScrollHandle(mcontext))
            .defaultPage(0)
            .load()

        return binding.root
    }

}