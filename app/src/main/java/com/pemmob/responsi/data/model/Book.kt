package com.pemmob.responsi.data.model

import com.google.gson.annotations.SerializedName

data class SearchResponse(
    @SerializedName("numFound") val numFound: Int? = null,
    @SerializedName("docs") val docs: List<Book>? = null
)

data class Book(
    @SerializedName("key") val key: String? = null,
    @SerializedName("title") val title: String? = null,
    @SerializedName("author_name") val authorName: List<String>? = null,
    @SerializedName("first_publish_year") val firstPublishYear: Int? = null,
    @SerializedName("edition_count") val editionCount: Int? = null,
    @SerializedName("language") val language: List<String>? = null
)