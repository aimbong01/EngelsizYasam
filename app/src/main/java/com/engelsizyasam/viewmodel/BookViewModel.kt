package com.engelsizyasam.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.engelsizyasam.database.BookDatabaseDao
import com.engelsizyasam.model.BookModel

class BookViewModel(dataSource: BookDatabaseDao) : ViewModel() {

    private val _navigateToBookDetail = MutableLiveData<Int?>()
    val navigateToBookDetail
        get() = _navigateToBookDetail

    fun onBookClicked(id: Int) {
        _navigateToBookDetail.value = id
    }

    fun onBookDetailNavigated() {
        _navigateToBookDetail.value = null
    }

    val database = dataSource
    val books = database.getAllBooks()

    suspend fun insert(bookModel: BookModel) {
        database.insert(bookModel)
    }

    suspend fun update(bookModel: BookModel) {
        database.update(bookModel)
    }

    suspend fun clear() {
        database.clear()
    }
}

class BookViewModelFactory(private val dataSource: BookDatabaseDao) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BookViewModel::class.java)) {
            return BookViewModel(dataSource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}