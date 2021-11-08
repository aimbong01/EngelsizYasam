package com.engelsizyasam.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NewsModel(
    val status: String? = null,
    val totalResults: Int? = null,
    val articles: List<Article?>? = null
) {
    @JsonClass(generateAdapter = true)
    data class Article(
        val source: Source? = null,
        val author: String? = null,
        val title: String? = null,
        val description: String? = null,
        val url: String? = null,
        val urlToImage: String? = null,
        val publishedAt: String? = null,
        val content: String? = null
    ) {
        @JsonClass(generateAdapter = true)
        data class Source(
            val id: Any? = null,
            val name: String? = null
        )
    }
}