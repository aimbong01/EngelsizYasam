package com.engelsizyasam.view

import android.media.AudioAttributes
import android.media.MediaPlayer
import androidx.lifecycle.*
import com.engelsizyasam.database.BookDatabaseDao
import com.engelsizyasam.model.BookModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BookVoiceDetailViewModel @Inject constructor(savedStateHandle: SavedStateHandle, database: BookDatabaseDao) : ViewModel() {


    var mediaPlayer: MediaPlayer? = null

    private val bookId = savedStateHandle.get<Int>("bookId")


    private val book: LiveData<BookModel> = database.getBookWithId(bookId!!)
    fun getBook() = book

    fun initialize(url: String) {
        mediaPlayer = MediaPlayer()
        mediaPlayer?.setAudioAttributes(
            AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .setUsage(AudioAttributes.USAGE_MEDIA)
                .build()
        )
        mediaPlayer?.setDataSource(url)
        mediaPlayer?.prepare()
    }

    fun playButton(url: String) {
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer()
            mediaPlayer?.setAudioAttributes(
                AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .build()
            )
            mediaPlayer?.setDataSource(url)
            mediaPlayer?.prepare()

        }
        mediaPlayer?.start()
    }

    fun pauseButton() {
        mediaPlayer?.pause()

    }

    fun stopButton() {
        mediaPlayer?.stop()
        mediaPlayer?.reset()
        mediaPlayer?.release()
        mediaPlayer = null
    }

    private val _currentDuration = MutableLiveData<String>()
    val currentDuration: LiveData<String>
        get() = _currentDuration

    fun onCurrent(current: String) {
        _currentDuration.value = current
    }

    private val _totalDuration = MutableLiveData<String>()
    val totalDuration: LiveData<String>
        get() = _totalDuration

    fun onTotal(total: String) {
        _totalDuration.value = total
    }
}

/*
class BookVoiceDetailViewModelFactory(private val bookId: Int, private val dataSource: BookDatabaseDao) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BookVoiceDetailViewModel::class.java)) {
            return BookVoiceDetailViewModel(bookId, dataSource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}*/
