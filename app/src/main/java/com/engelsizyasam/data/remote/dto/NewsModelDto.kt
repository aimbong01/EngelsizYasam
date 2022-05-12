package com.engelsizyasam.data.remote.dto

import com.engelsizyasam.domain.model.News
import com.engelsizyasam.domain.model.Series
import com.engelsizyasam.domain.model.SeriesDetail

data class NewsModelDto(
    val id: String?,
    val author: String?,
    val title: String?,
    val description: String?,
    val url: String?,
    val urlToImage: String?,
    val publishedAt: String?,
    val content: String?,

    val totalResults: Int,

    val snippet: NewsModelDto?,

    val thumbnails: NewsModelDto?,

    val medium: NewsModelDto?,


    )


fun NewsModelDto.toNews(): News {
    return News(
        title = title,
        urlToImage = urlToImage,
        url = url
    )
}

fun NewsModelDto.toSeries(): Series {
    return Series(
        id = id,
        title = snippet?.title,
        url = snippet?.thumbnails?.medium?.url
    )
}

fun NewsModelDto.toSeriesDetail(): SeriesDetail {
    return SeriesDetail(
        title = snippet?.title,
        url = snippet?.thumbnails?.medium?.url
    )
}
