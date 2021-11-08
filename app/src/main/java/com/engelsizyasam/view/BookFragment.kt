package com.engelsizyasam.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.engelsizyasam.R
import com.engelsizyasam.adapter.BookAdapter
import com.engelsizyasam.adapter.BookPdfClickListener
import com.engelsizyasam.adapter.BookVoiceClickListener
import com.engelsizyasam.database.BookDatabase
import com.engelsizyasam.model.BookModel
import com.engelsizyasam.databinding.FragmentBookBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class BookFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentBookBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_book, container, false)

        val application = requireNotNull(this.activity).application
        //val dataSource = BookDatabase.getInstance(application).bookDatabaseDao
        //val viewModelFactory = BookViewModelFactory(dataSource)

        val bookViewModel = ViewModelProvider(this).get(BookViewModel::class.java)
        binding.viewModel = bookViewModel

        val adapter = BookAdapter(application,
            BookPdfClickListener { bookId -> bookViewModel.onBookClicked(bookId) },
            BookVoiceClickListener { bookId -> bookViewModel.onBookVoiceClicked(bookId) }
        )

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

        bookViewModel.navigateToBookDetail.observe(viewLifecycleOwner, {
            it?.let {
                this.findNavController().navigate(BookFragmentDirections.actionBookFragmentToBookDetailFragment(it))
                bookViewModel.onBookDetailNavigated()
            }
        })

        bookViewModel.navigateToBookVoiceDetail.observe(viewLifecycleOwner, {
            it?.let {
                this.findNavController().navigate(BookFragmentDirections.actionBookFragmentToBookVoiceDetailFragment(it))
                bookViewModel.onBookVoiceDetailNavigated()
            }
        })


        viewLifecycleOwner.lifecycleScope.launch {
            try {
                bookViewModel.insert(
                    BookModel(
                        bookName = "Kürk Mantolu Madonna",
                        bookAuthor = "Sabahattin Ali",
                        bookImage = "madonna",
                        bookPDF = "madonna.pdf",
                        bookVoiceUrl = "https://ia802903.us.archive.org/28/items/sabahattinalikurkmantolumadonna/Sabahattin%20Ali%20-%20Ku%CC%88rk%20Mantolu%20Madonna.webm",
                        bookPageSize = "164"
                    )
                )
                bookViewModel.insert(
                    BookModel(
                        bookName = "İçimizdeki Çocuk",
                        bookAuthor = "Doğan Cüceloğlu",
                        bookImage = "icimizdekicocuk",
                        bookPDF = "icimizdekicocuk.pdf",
                        bookVoiceUrl = "https://ia803402.us.archive.org/16/items/dogan-cuceloglu-icimizdeki-cocuk/Dog%CC%86an%20Cu%CC%88celog%CC%86lu%20-%20I%CC%87c%CC%A7imizdeki%20C%CC%A7ocuk.mp4",
                        bookPageSize = "259"

                    )
                )
                bookViewModel.insert(
                    BookModel(
                        bookName = "Şeker Portakalı",
                        bookAuthor = "José Mauro de Vasconcelos",
                        bookImage = "sekerportakali",
                        bookPDF = "sekerportakali.pdf",
                        bookVoiceUrl = "https://ia802909.us.archive.org/28/items/sonulkucuyukimoldurdumuhsinyazicioglu/S%CC%A7eker%20Portakal%C4%B1%20-%20Jose%20Mauro%20de%20Vasconcelos%20.mp4",
                        bookPageSize = "365"
                    )
                )
                bookViewModel.insert(
                    BookModel(
                        bookName = "Suç ve Ceza",
                        bookAuthor = "Fyodor Mihailoviç Dostoyevski",
                        bookImage = "sucveceza",
                        bookPDF = "sucveceza.pdf",
                        bookVoiceUrl = "https://ia802800.us.archive.org/2/items/fyodordostoyevski-sucvecezatekparca/Fyodor%20Dostoyevski%20%E2%80%93%20Suc%CC%A7%20ve%20Ceza%20.webm",
                        bookPageSize = "1300"
                    )
                )
                bookViewModel.insert(
                    BookModel(
                        bookName = "Simyacı",
                        bookAuthor = "Paulo Coelho",
                        bookImage = "simyaci",
                        bookPDF = "simyaci.pdf",
                        bookVoiceUrl = "https://ia903106.us.archive.org/1/items/paulocoelhosimyaci_201912/Paulo%20Coelho%20-%20Simyac%C4%B1.mp3",
                        bookPageSize = "295"
                    )
                )

            } catch (e: Exception) {

            }
        }

        return binding.root
    }

}