package com.engelsizyasam.presentation.seriesdetail

import android.util.Log
import androidx.lifecycle.*
import com.engelsizyasam.domain.model.SeriesDetailModel
import com.engelsizyasam.network.SeriesDetailApiService

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SeriesDetailViewModel @Inject constructor(savedStateHandle: SavedStateHandle,private val service: SeriesDetailApiService) : ViewModel() {

    private lateinit var base: SeriesDetailModel
    private var total: Int = 0
    private var page: Int = 0
    private var seriesPage: String = ""

    private val _seriesName = savedStateHandle.get<String>("seriesName").toString()
    val seriesName: String
        get() = _seriesName

    private var playlistId = savedStateHandle.get<String>("playlistId").toString()

    private val _properties = MutableLiveData<List<SeriesDetailModel.İtem>>()
    val properties: LiveData<List<SeriesDetailModel.İtem>>
        get() = _properties

    fun run() {
        viewModelScope.launch {
            total = service.getProperties(pageToken = seriesPage, playlistId = playlistId).pageInfo.totalResults
            page = if (total % 50 == 0)
                total / 50
            else
                (total / 50) + 1
            Log.e("total", total.toString())

            try {
                for (i in 1..page) {
                    base = service.getProperties(pageToken = seriesPage, playlistId = playlistId)
                    _properties.value = base.items
                    seriesPage = base.nextPageToken
                }

                _properties.value = listOf()

            } catch (e: Exception) {
                //Toast.makeText(application, "İnternet Bağlantınızı Kontrol Edin", Toast.LENGTH_SHORT).show()
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

/*
class SeriesDetailViewModelFactory(private val playlistId: String, private val seriesName: String) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SeriesDetailViewModel::class.java)) {
            return SeriesDetailViewModel(playlistId, seriesName) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}*/
