package com.engelsizyasam.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ScholarModel(
    @Json(name = "search_metadata")
    val searchMetadata: SearchMetadata = SearchMetadata(),
    @Json(name = "search_parameters")
    val searchParameters: SearchParameters = SearchParameters(),
    @Json(name = "search_information")
    val searchİnformation: Searchİnformation = Searchİnformation(),
    val profiles: Profiles = Profiles(),
    @Json(name = "organic_results")
    val organicResults: List<OrganicResult> = listOf(),
    val pagination: Pagination = Pagination(),
    @Json(name = "serpapi_pagination")
    val serpapiPagination: SerpapiPagination = SerpapiPagination()
) {
    @JsonClass(generateAdapter = true)
    data class SearchMetadata(
        val id: String = "",
        val status: String = "",
        @Json(name = "json_endpoint")
        val jsonEndpoint: String = "",
        @Json(name = "created_at")
        val createdAt: String = "",
        @Json(name = "processed_at")
        val processedAt: String = "",
        @Json(name = "google_scholar_url")
        val googleScholarUrl: String = "",
        @Json(name = "raw_html_file")
        val rawHtmlFile: String = "",
        @Json(name = "total_time_taken")
        val totalTimeTaken: Double = 0.0
    )

    @JsonClass(generateAdapter = true)
    data class SearchParameters(
        val engine: String = "",
        val q: String = "",
        val hl: String = ""
    )

    @JsonClass(generateAdapter = true)
    data class Searchİnformation(
        @Json(name = "organic_results_state")
        val organicResultsState: String = "",
        @Json(name = "total_results")
        val totalResults: Int = 0,
        @Json(name = "time_taken_displayed")
        val timeTakenDisplayed: Double = 0.0,
        @Json(name = "query_displayed")
        val queryDisplayed: String = ""
    )

    @JsonClass(generateAdapter = true)
    data class Profiles(
        val link: String = "",
        @Json(name = "serpapi_link")
        val serpapiLink: String = ""
    )

    @JsonClass(generateAdapter = true)
    data class OrganicResult(
        val position: Int = 0,
        val title: String = "",
        @Json(name = "result_id")
        val resultİd: String = "",
        val type: String = "",
        val link: String = "",
        val snippet: String = "",
        @Json(name = "publication_info")
        val publicationİnfo: Publicationİnfo = Publicationİnfo(),
        val resources: List<Resource> = listOf(),
        @Json(name = "inline_links")
        val inlineLinks: İnlineLinks = İnlineLinks()
    ) {
        @JsonClass(generateAdapter = true)
        data class Publicationİnfo(
            val summary: String = "",
            val authors: List<Author> = listOf()
        ) {
            @JsonClass(generateAdapter = true)
            data class Author(
                val name: String = "",
                val link: String = "",
                @Json(name = "serpapi_scholar_link")
                val serpapiScholarLink: String = "",
                @Json(name = "author_id")
                val authorİd: String = ""
            )
        }

        @JsonClass(generateAdapter = true)
        data class Resource(
            val title: String = "",
            @Json(name = "file_format")
            val fileFormat: String = "",
            val link: String = ""
        )

        @JsonClass(generateAdapter = true)
        data class İnlineLinks(
            @Json(name = "serpapi_cite_link")
            val serpapiCiteLink: String = "",
            @Json(name = "cited_by")
            val citedBy: CitedBy = CitedBy(),
            @Json(name = "related_pages_link")
            val relatedPagesLink: String = "",
            val versions: Versions = Versions(),
            @Json(name = "cached_page_link")
            val cachedPageLink: String = ""
        ) {
            @JsonClass(generateAdapter = true)
            data class CitedBy(
                val total: Int = 0,
                val link: String = "",
                @Json(name = "cites_id")
                val citesİd: String = "",
                @Json(name = "serpapi_scholar_link")
                val serpapiScholarLink: String = ""
            )

            @JsonClass(generateAdapter = true)
            data class Versions(
                val total: Int = 0,
                val link: String = "",
                @Json(name = "cluster_id")
                val clusterİd: String = "",
                @Json(name = "serpapi_scholar_link")
                val serpapiScholarLink: String = ""
            )
        }
    }

    @JsonClass(generateAdapter = true)
    data class Pagination(
        val current: Int = 0,
        val next: String = "",
        @Json(name = "other_pages")
        val otherPages: Map<Int,String> = mapOf()
    )

    @JsonClass(generateAdapter = true)
    data class SerpapiPagination(
        val current: Int = 0,
        @Json(name = "next_link")
        val nextLink: String = "",
        val next: String = "",
        @Json(name = "other_pages")
        val otherPages: Map<Int,String> = mapOf()
    )
}