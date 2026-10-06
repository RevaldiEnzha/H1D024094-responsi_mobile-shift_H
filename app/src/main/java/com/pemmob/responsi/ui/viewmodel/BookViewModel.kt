package com.pemmob.responsi.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pemmob.responsi.data.model.Book
import com.pemmob.responsi.data.repository.BookRepository
import com.pemmob.responsi.util.Constants
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed interface BookUiState {
    data object Loading : BookUiState
    data class Success(val books: List<Book>) : BookUiState
    data class Error(val message: String) : BookUiState
}

class BookViewModel : ViewModel() {
    private val repository = BookRepository()

    private val _uiState = MutableStateFlow<BookUiState>(BookUiState.Loading)
    val uiState: StateFlow<BookUiState> = _uiState.asStateFlow()

    private val _query = MutableStateFlow(Constants.DEFAULT_QUERY)
    val query: StateFlow<String> = _query.asStateFlow()

    private var searchJob: Job? = null

    init {
        searchBooks()
    }

    fun onQueryChange(newQuery: String) {
        _query.value = newQuery
    }

    fun searchBooks() {
        val keyword = _query.value.trim()
        if (keyword.isEmpty()) {
            _uiState.value = BookUiState.Error("Kata kunci tidak boleh kosong")
            return
        }

        searchJob?.cancel() // batalkan pencarian sebelumnya
        searchJob = viewModelScope.launch {
            _uiState.value = BookUiState.Loading
            _uiState.value = try {
                BookUiState.Success(repository.searchBooks(keyword))
            } catch (e: CancellationException) {
                throw e
            } catch (e: IOException) {
                BookUiState.Error("Tidak dapat terhubung ke internet")
            } catch (e: HttpException) {
                BookUiState.Error("Server bermasalah (kode ${e.code()})")
            } catch (e: Exception) {
                BookUiState.Error("Terjadi kesalahan: ${e.localizedMessage}")
            }
        }
    }
}