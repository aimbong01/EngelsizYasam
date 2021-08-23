package com.engelsizyasam.bookdetail

import android.content.Context
import androidx.lifecycle.ViewModel
import com.engelsizyasam.bookDatabase.DBHelper

class BookDetailViewModel(private val bookId: Int = 0, context: Context) : ViewModel() {

    val db by lazy { DBHelper(context) }
    val bookList = db.readData()
    var bookName: String = ""
    var pdfName: String = ""

    init {
        islem()
    }

    fun islem() {
        for (list in bookList) {
            if (list.bookId == bookId) {
                bookName = list.bookName
                pdfName = list.bookPDF
                break
            }
        }
    }
}