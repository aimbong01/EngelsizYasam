package com.engelsizyasam.data.remote.dto

import com.engelsizyasam.domain.model.News

data class NewsModelDto(
    val author: String?,
    val title: String?,
    val description: String?,
    val url: String?,
    val urlToImage: String?,
    val publishedAt: String?,
    val content: String?
)


fun NewsModelDto.toNews(): News {
    return News(
        title = title,
        urlToImage = urlToImage,
        url = url
    )
}
