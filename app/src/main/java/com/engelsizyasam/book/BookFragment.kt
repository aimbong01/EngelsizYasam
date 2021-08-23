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

        val bookViewModel = ViewModelProvider(this).get(BookViewModel::class.java)

        val adapter = RecyclerAdapter(mcontext, SleepNightListener { bookId ->
            //Toast.makeText(context, "${bookId}", Toast.LENGTH_LONG).show()
            bookViewModel.onBookClicked(bookId)

        })
//        val adapter = RecyclerAdapter(mcontext)
        binding.recyclerView.adapter = adapter

        bookViewModel.navigateToBookDetail.observe(viewLifecycleOwner, Observer { night ->
            night?.let {
                this.findNavController().navigate(BookFragmentDirections.actionBookFragmentToBookDetailFragment(night))
                bookViewModel.onBookDetailNavigated()
            }
        })







        return binding.root
    }

}