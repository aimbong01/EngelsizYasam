package com.engelsizyasam.presentation.series

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.engelsizyasam.domain.model.Series
import com.engelsizyasam.network.SeriesApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SeriesViewModel @Inject constructor(private val service: SeriesApiService) : ViewModel() {

    private lateinit var base: Series
    private var total: Int = 0
    private var page: Int = 0
    private var seriesPage: String = ""
    var seriesName: String = ""

    private val _properties = MutableLiveData<List<Series.İtem>>()
    val properties: LiveData<List<Series.İtem>>
        get() = _properties

    private var job = Job() as Job

    override fun onCleared() {
        Log.e("cancel", "cancel")
        job.cancel()
        super.onCleared()
    }

    init {
        _properties.value = listOf()
        job = viewModelScope.launch {
            total = service.getProperties(pageToken = seriesPage).pageInfo.totalResults
            page = if (total % 5 == 0)
                total / 5
            else
                (total / 5) + 1

            try {
                for (i in 1..page) {
                    base = service.getProperties(pageToken = seriesPage)
                    _properties.value = _properties.value?.plus(base.items)
                    seriesPage = base.nextPageToken
                }

            } catch (e: Exception) {
                //Log.e("hata", e.toString())
                //Toast.makeText(application, "İnternet Bağlantınızı Kontrol Edin", Toast.LENGTH_SHORT).show()
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

