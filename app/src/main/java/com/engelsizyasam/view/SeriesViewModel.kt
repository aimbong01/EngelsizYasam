package com.engelsizyasam.view

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.*
import com.engelsizyasam.model.SeriesModel
import com.engelsizyasam.network.SeriesApi
import kotlinx.coroutines.launch

class SeriesViewModel(private val application: Application) : ViewModel() {

    private lateinit var base: SeriesModel
    private var total: Int = 0
    private var page: Int = 0
    private var seriesPage: String = ""
    var seriesName: String = ""

    private val _properties = MutableLiveData<List<SeriesModel.İtem>>()
    val properties: LiveData<List<SeriesModel.İtem>>
        get() = _properties

    fun run() {
        viewModelScope.launch {
            total = SeriesApi.retrofitService.getProperties(pageToken = seriesPage).pageInfo.totalResults
            page = if (total % 5 == 0)
                total / 5
            else
                (total / 5) + 1
            Log.e("total", total.toString())

            try {
                for (i in 1..page) {
                    base = SeriesApi.retrofitService.getProperties(pageToken = seriesPage)
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

    private val _navigateToSeriesDetail = MutableLiveData<String?>()
    val navigateToSeriesDetail
        get() = _navigateToSeriesDetail

    fun onSeriesClicked(id: String) {
        _navigateToSeriesDetail.value = id
    }

    fun onSeriesDetailNavigated() {
        _navigateToSeriesDetail.value = null
    }

}

class SeriesViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SeriesViewModel::class.java)) {
            return SeriesViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}