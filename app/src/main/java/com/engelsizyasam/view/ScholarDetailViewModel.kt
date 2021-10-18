package com.engelsizyasam.view

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ScholarDetailViewModel(private val application: Application, val link: String) : ViewModel() {

}

class ScholarDetailViewModelFactory(private val application: Application, private val link: String) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ScholarDetailViewModel::class.java)) {
            return ScholarDetailViewModel(application, link) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}