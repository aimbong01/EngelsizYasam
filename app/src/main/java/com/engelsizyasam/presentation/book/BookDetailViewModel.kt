package com.engelsizyasam.presentation.book

import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.engelsizyasam.data.local.BookDao
import com.engelsizyasam.domain.model.BookModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BookDetailViewModel @Inject constructor(savedStateHandle: SavedStateHandle, database: BookDao) : ViewModel() {

    private val bookId = savedStateHandle.get<Int>("bookId")

    private val book: LiveData<BookModel> = database.getBookWithId(bookId!!)
    fun getBook() = book
}