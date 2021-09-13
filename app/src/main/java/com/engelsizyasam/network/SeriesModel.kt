package com.engelsizyasam.network


import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SeriesModel(
    val kind: String = "",
    val etag: String = "",
    val nextPageToken: String = "",
    val items: List<İtem> = listOf(),
    val pageInfo: PageInfo = PageInfo()
) {
    @JsonClass(generateAdapter = true)
    data class İtem(
        val kind: String = "",
        val etag: String = "",
        val id: String = "",
        val snippet: Snippet = Snippet()
    ) {
        @JsonClass(generateAdapter = true)
        data class Snippet(
            val publishedAt: String = "",
            val channelId: String = "",
            val title: String = "",
            val description: String = "",
            val thumbnails: Thumbnails = Thumbnails(),
            val channelTitle: String = "",
            val playlistId: String = "",
            val position: Int = 0,
            val resourceId: ResourceId = ResourceId(),
            val videoOwnerChannelTitle: String = "",
            val videoOwnerChannelId: String = ""
        ) {
            @JsonClass(generateAdapter = true)
            data class Thumbnails(
                val default: Default = Default(),
                val medium: Medium = Medium(),
                val high: High = High(),
                val standard: Standard = Standard(),
                val maxres: Maxres = Maxres()
            ) {
                @JsonClass(generateAdapter = true)
                data class Default(
                    val url: String = "",
                    val width: Int = 0,
                    val height: Int = 0
                )

                @JsonClass(generateAdapter = true)
                data class Medium(
                    val url: String = "",
                    val width: Int = 0,
                    val height: Int = 0
                )

                @JsonClass(generateAdapter = true)
                data class High(
                    val url: String = "",
                    val width: Int = 0,
                    val height: Int = 0
                )

                @JsonClass(generateAdapter = true)
                data class Standard(
                    val url: String = "",
                    val width: Int = 0,
                    val height: Int = 0
                )

                @JsonClass(generateAdapter = true)
                data class Maxres(
                    val url: String = "",
                    val width: Int = 0,
                    val height: Int = 0
                )
            }

            @JsonClass(generateAdapter = true)
            data class ResourceId(
                val kind: String = "",
                val videoId: String = ""
            )
        }
    }

    @JsonClass(generateAdapter = true)
    data class PageInfo(
        val totalResults: Int = 0,
        val resultsPerPage: Int = 0
    )
}