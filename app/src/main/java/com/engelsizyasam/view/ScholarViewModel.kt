package com.engelsizyasam.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.engelsizyasam.model.ScholarModel
import com.engelsizyasam.network.ScholarApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScholarViewModel @Inject constructor(): ViewModel() {
    private val _properties = MutableLiveData<List<ScholarModel.OrganicResult>>()
    val properties: LiveData<List<ScholarModel.OrganicResult>>
        get() = _properties

    private val _openLink = MutableLiveData<String?>()
    val openLink
        get() = _openLink

    fun onLinkClicked(id: String) {
        _openLink.value = id
    }

    fun onLinkClickCompleted() {
        _openLink.value = null
    }

    fun run() {
        viewModelScope.launch {
            try {
                for (i in 0..100 step 10) {
                    _properties.value = ScholarApi.retrofitService.getProperties(start = i.toString()).organicResults
                }
            } catch (e: Exception) {
                //Toast.makeText(application, "İnternet Bağlantınızı Kontrol Edin", Toast.LENGTH_SHORT).show()
            }

        }
    }
}