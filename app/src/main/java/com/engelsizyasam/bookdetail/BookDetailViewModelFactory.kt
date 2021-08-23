package com.engelsizyasam.bookdetail

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class BookDetailViewModelFactory(private val bookId: Int, val context: Context) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BookDetailViewModel::class.java)) {
            return BookDetailViewModel(bookId,context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}


