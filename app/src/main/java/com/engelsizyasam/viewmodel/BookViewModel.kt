package com.engelsizyasam.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.engelsizyasam.database.BookDatabaseDao
import com.engelsizyasam.database.BookModel
import kotlinx.coroutines.launch

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

    private suspend fun insert(bookModel: BookModel) {
        database.insert(bookModel)
    }

    private suspend fun update(bookModel: BookModel) {
        database.update(bookModel)
    }

    private suspend fun clear() {
        database.clear()
    }

    init {
        querys()
    }

    fun querys() {
        viewModelScope.launch {
            try {
                insert(BookModel(bookName = "Kürk Mantolu Madonna", bookAuthor = "Sabahattin Ali", bookImage = "madonna", bookPDF = "madonna.pdf"))
                insert(BookModel(bookName = "İçimizdeki Çocuk", bookAuthor = "Doğan Cüceloğlu", bookImage = "icimizdekicocuk", bookPDF = "icimizdekicocuk.pdf"))
                insert(BookModel(bookName = "Şeker Portakalı", bookAuthor = "José Mauro de Vasconcelos", bookImage = "sekerportakali", bookPDF = "sekerportakali.pdf"))
                insert(BookModel(bookName = "Suç ve Ceza", bookAuthor = "Fyodor Mihailoviç Dostoyevski", bookImage = "sucveceza", bookPDF = "sucveceza.pdf"))
                insert(BookModel(bookName = "Simyacı", bookAuthor = "Paulo Coelho", bookImage = "simyaci", bookPDF = "simyaci.pdf"))

            }catch (e:Exception){

            }

        }
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