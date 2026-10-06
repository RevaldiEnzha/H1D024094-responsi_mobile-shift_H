package com.pemmob.responsi.data.repository

import com.pemmob.responsi.data.model.Book
import com.pemmob.responsi.data.network.ApiClient
import com.pemmob.responsi.data.network.BookApiService
import com.pemmob.responsi.util.Constants

class BookRepository(
    private val api: BookApiService = ApiClient.instance
) {
    suspend fun searchBooks(query: String): List<Book> =
        api.searchBooks(query, Constants.SEARCH_LIMIT).docs.orEmpty()
}