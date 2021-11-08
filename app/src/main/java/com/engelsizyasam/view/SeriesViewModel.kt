package com.engelsizyasam.view

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.*
import com.engelsizyasam.BaseApplication
import com.engelsizyasam.model.SeriesModel
import com.engelsizyasam.network.SeriesApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SeriesViewModel @Inject constructor(private val application: BaseApplication, private val service: SeriesApiService) : ViewModel() {

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
            total = service.getProperties(pageToken = seriesPage).pageInfo.totalResults
            page = if (total % 5 == 0)
                total / 5
            else
                (total / 5) + 1
            Log.e("total", total.toString())

            try {
                for (i in 1..page) {
                    base = service.getProperties(pageToken = seriesPage)
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

