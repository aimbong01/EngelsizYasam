package com.engelsizyasam.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.engelsizyasam.R
import com.engelsizyasam.adapter.BookAdapter
import com.engelsizyasam.adapter.BookListener
import com.engelsizyasam.database.BookDatabase
import com.engelsizyasam.database.BookModel
import com.engelsizyasam.databinding.FragmentBookBinding
import com.engelsizyasam.viewmodel.BookViewModel
import com.engelsizyasam.viewmodel.BookViewModelFactory
import kotlinx.coroutines.launch

class BookFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentBookBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_book, container, false)

        val application = requireNotNull(this.activity).application
        val dataSource = BookDatabase.getInstance(application).bookDatabaseDao
        val viewModelFactory = BookViewModelFactory(dataSource)

        val bookViewModel = ViewModelProvider(this, viewModelFactory).get(BookViewModel::class.java)
        binding.viewModel = bookViewModel

        val adapter = BookAdapter(application, BookListener { bookId ->
            bookViewModel.onBookClicked(bookId)
        })

        binding.recyclerView.adapter = adapter

        bookViewModel.books.observe(viewLifecycleOwner, {
            adapter.countryList = it
        })

        binding.search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(query: String): Boolean {
                adapter.getFilter().filter(query)
                return true
            }
        })

        binding.lifecycleOwner = this

        bookViewModel.navigateToBookDetail.observe(viewLifecycleOwner, { night ->
            night?.let {
                this.findNavController().navigate(BookFragmentDirections.actionBookFragmentToBookDetailFragment(night))
                bookViewModel.onBookDetailNavigated()
            }
        })


        viewLifecycleOwner.lifecycleScope.launch {
            try {
                bookViewModel.insert(
                    BookModel(
                        bookName = "Kürk Mantolu Madonna",
                        bookAuthor = "Sabahattin Ali",
                        bookImage = "madonna",
                        bookPDF = "madonna.pdf"
                    )
                )
                bookViewModel.insert(
                    BookModel(
                        bookName = "İçimizdeki Çocuk",
                        bookAuthor = "Doğan Cüceloğlu",
                        bookImage = "icimizdekicocuk",
                        bookPDF = "icimizdekicocuk.pdf"
                    )
                )
                bookViewModel.insert(
                    BookModel(
                        bookName = "Şeker Portakalı",
                        bookAuthor = "José Mauro de Vasconcelos",
                        bookImage = "sekerportakali",
                        bookPDF = "sekerportakali.pdf"
                    )
                )
                bookViewModel.insert(
                    BookModel(
                        bookName = "Suç ve Ceza",
                        bookAuthor = "Fyodor Mihailoviç Dostoyevski",
                        bookImage = "sucveceza",
                        bookPDF = "sucveceza.pdf"
                    )
                )
                bookViewModel.insert(BookModel(bookName = "Simyacı", bookAuthor = "Paulo Coelho", bookImage = "simyaci", bookPDF = "simyaci.pdf"))

            } catch (e: Exception) {

            }
        }

        return binding.root
    }

}