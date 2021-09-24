package com.engelsizyasam.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.*
import com.engelsizyasam.network.ContentApi
import com.engelsizyasam.model.SeriesDetailModel
import kotlinx.coroutines.launch

class SeriesDetailViewModel(private val application: Application, private val playlistId: String) : ViewModel() {

    var seriesName: String = ""
    var seriesPage: Int = 1
    var pageToken: List<String> = listOf("EAEaBlBUOkNESQ", "EAAaBlBUOkNESQ", "EAAaBlBUOkNHUQ", "EAAaB1BUOkNKWUI")

    private val _properties = MutableLiveData<List<SeriesDetailModel.İtem>>()
    val properties: LiveData<List<SeriesDetailModel.İtem>>
        get() = _properties

    fun run() {
        viewModelScope.launch {
            try {
                for (i in 1..seriesPage) {
                    _properties.value =
                        ContentApi.retrofitService.getProperties(pageToken[i-1], "50", "snippet", playlistId).items
                }
            } catch (e: Exception) {
                Toast.makeText(application, "İnternet Bağlantınızı Kontrol Edin", Toast.LENGTH_SHORT).show()
            }

        }
    }

    private val _openVideoLink = MutableLiveData<String?>()
    val openVideoLink
        get() = _openVideoLink

    fun onLinkClicked(id: String) {
        _openVideoLink.value = id
    }

    fun onLinkClickCompleted() {
        _openVideoLink.value = null
    }
}

class SeriesDetailViewModelFactory(private val application: Application, private val playlistId: String) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SeriesDetailViewModel::class.java)) {
            return SeriesDetailViewModel(application, playlistId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}