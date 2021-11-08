package com.engelsizyasam.view

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.engelsizyasam.BaseApplication
import com.engelsizyasam.model.NewsModel
import com.engelsizyasam.network.NewsApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(private val application: BaseApplication, private val service: NewsApiService) : ViewModel() {

    private val _properties = MutableLiveData<List<NewsModel.Article?>>()
    val properties: LiveData<List<NewsModel.Article?>>
        get() = _properties

    fun run() {
        viewModelScope.launch {
            try {
                _properties.value = service.getProperties().articles!!

            } catch (e: Exception) {
                Toast.makeText(application, "İnternet Bağlantınızı Kontrol Edin", Toast.LENGTH_SHORT).show()

            }

        }
    }
}