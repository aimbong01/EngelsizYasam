package com.engelsizyasam.book

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class BookViewModel : ViewModel() {

    private val _navigateToBookDetail = MutableLiveData<Int?>()
    val navigateToBookDetail
        get() = _navigateToBookDetail

    fun onBookClicked(id: Int) {
        _navigateToBookDetail.value = id
    }

    fun onBookDetailNavigated() {
        _navigateToBookDetail.value = null
    }
}