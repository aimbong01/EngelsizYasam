package com.engelsizyasam.view

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.*
import com.engelsizyasam.model.SeriesDetailModel
import com.engelsizyasam.network.SeriesDetailApi
import kotlinx.coroutines.launch

class SeriesDetailViewModel(private val application: Application, private val playlistId: String, var seriesName: String) : ViewModel() {

    private lateinit var base: SeriesDetailModel
    private var total: Int = 0
    private var page: Int = 0
    private var seriesPage: String = ""

    private val _properties = MutableLiveData<List<SeriesDetailModel.İtem>>()
    val properties: LiveData<List<SeriesDetailModel.İtem>>
        get() = _properties


    fun run() {
        viewModelScope.launch {
            total = SeriesDetailApi.retrofitService.getProperties(pageToken = seriesPage, playlistId = playlistId).pageInfo.totalResults
            page = if (total % 50 == 0)
                total / 50
            else
                (total / 50) + 1
            Log.e("total", total.toString())

            try {
                for (i in 1..page) {
                    base = SeriesDetailApi.retrofitService.getProperties(pageToken = seriesPage, playlistId = playlistId)
                    _properties.value = base.items
                    seriesPage = base.nextPageToken
                    //Log.e("seriespage", seriesPage)
                }

                _properties.value = listOf()

            } catch (e: Exception) {
                Log.e("hata", "hata")
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

class SeriesDetailViewModelFactory(private val application: Application, private val playlistId: String, private val seriesName: String) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SeriesDetailViewModel::class.java)) {
            return SeriesDetailViewModel(application, playlistId, seriesName) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}