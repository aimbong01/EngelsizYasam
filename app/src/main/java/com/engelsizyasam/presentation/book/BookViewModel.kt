package com.engelsizyasam.presentation.book

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.engelsizyasam.data.local.BookDao
import com.engelsizyasam.domain.model.BookModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BookViewModel @Inject constructor(private val dataSource: BookDao) : ViewModel() {

    val books = dataSource.getAllBooks()

    suspend fun insert(bookModel: BookModel) {
        dataSource.insert(bookModel)
    }
}

