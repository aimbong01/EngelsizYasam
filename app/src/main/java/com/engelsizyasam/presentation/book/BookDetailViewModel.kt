package com.engelsizyasam.presentation.book

import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.engelsizyasam.database.BookDatabaseDao
import com.engelsizyasam.domain.model.BookModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BookDetailViewModel @Inject constructor(savedStateHandle: SavedStateHandle, private val database: BookDatabaseDao) : ViewModel() {

    private val bookId = savedStateHandle.get<Int>("bookId")

    private val book: LiveData<BookModel> = database.getBookWithId(bookId!!)
    fun getBook() = book




}

/*
class BookDetailViewModelFactory(private val bookId: Int, private val dataSource: BookDatabaseDao) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BookDetailViewModel::class.java)) {
            return BookDetailViewModel(bookId, dataSource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}*/
