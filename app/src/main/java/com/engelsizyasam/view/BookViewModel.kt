package com.engelsizyasam.view

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.engelsizyasam.database.BookDatabaseDao
import com.engelsizyasam.model.BookModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BookViewModel @Inject constructor(private val dataSource: BookDatabaseDao) : ViewModel() {

    val books = dataSource.getAllBooks()

    suspend fun insert(bookModel: BookModel) {
        dataSource.insert(bookModel)
    }

    suspend fun update(bookModel: BookModel) {
        dataSource.update(bookModel)
    }

    suspend fun clear() {
        dataSource.clear()
    }

    private val _navigateToBookDetail = MutableLiveData<Int?>()
    val navigateToBookDetail
        get() = _navigateToBookDetail

    private val _navigateToBookVoiceDetail = MutableLiveData<Int?>()
    val navigateToBookVoiceDetail
        get() = _navigateToBookVoiceDetail

}

