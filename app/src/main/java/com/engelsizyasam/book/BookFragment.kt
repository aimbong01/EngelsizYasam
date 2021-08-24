package com.engelsizyasam.book

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.engelsizyasam.R
import com.engelsizyasam.database.BookDatabase
import com.engelsizyasam.databinding.BookFragmentBinding

class BookFragment : Fragment() {

    lateinit var mcontext: Context

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mcontext = context

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: BookFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.book_fragment, container, false)

        val application = requireNotNull(this.activity).application
        val dataSource = BookDatabase.getInstance(application).bookDatabaseDao
        val viewModelFactory = BookViewModelFactory(dataSource)

        val bookViewModel = ViewModelProvider(this, viewModelFactory).get(BookViewModel::class.java)
        binding.viewModel = bookViewModel

        val adapter = RecyclerAdapter(mcontext, SleepNightListener { bookId ->
            bookViewModel.onBookClicked(bookId)
        })

        binding.recyclerView.adapter = adapter

        bookViewModel.books.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.data = it
            }
        })

        binding.setLifecycleOwner(this)

        bookViewModel.navigateToBookDetail.observe(viewLifecycleOwner, Observer { night ->
            night?.let {
                this.findNavController().navigate(BookFragmentDirections.actionBookFragmentToBookDetailFragment(night))
                bookViewModel.onBookDetailNavigated()
            }
        })



        return binding.root
    }

}