package com.engelsizyasam.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.engelsizyasam.database.BookDatabaseDao
import com.engelsizyasam.database.BookModel

class BookDetailViewModel(bookId: Int, dataSource: BookDatabaseDao) : ViewModel() {

    val database = dataSource
    private val book: LiveData<BookModel> = database.getBookWithId(bookId)
    fun getBook() = book


}

class BookDetailViewModelFactory(private val bookId: Int, private val dataSource: BookDatabaseDao) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BookDetailViewModel::class.java)) {
            return BookDetailViewModel(bookId, dataSource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}