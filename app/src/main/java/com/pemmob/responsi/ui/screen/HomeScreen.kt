package com.pemmob.responsi.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import com.pemmob.responsi.data.model.Book
import com.pemmob.responsi.ui.component.BookItemCard
import com.pemmob.responsi.ui.component.EmptyView
import com.pemmob.responsi.ui.component.ErrorView
import com.pemmob.responsi.ui.component.LoadingView
import com.pemmob.responsi.ui.component.SearchBar
import com.pemmob.responsi.ui.viewmodel.BookUiState
import com.pemmob.responsi.ui.viewmodel.BookViewModel

// STATEFUL
@Composable
fun HomeScreen(
    viewModel: BookViewModel,
    onBookClick: (Int) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val query by viewModel.query.collectAsState()
    val focusManager = LocalFocusManager.current

    StatelessHomeScreen(
        uiState = uiState,
        query = query,
        onQueryChange = viewModel::onQueryChange,
        onSearch = {
            focusManager.clearFocus()
            viewModel.searchBooks()
        },
        onBookClick = onBookClick
    )
}

// STATELESS
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StatelessHomeScreen(
    uiState: BookUiState,
    query: String,
    onQueryChange: (String) -> Unit,
    onSearch: () -> Unit,
    onBookClick: (Int) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Katalog Buku") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            SearchBar(
                query = query,
                onQueryChange = onQueryChange,
                onSearch = onSearch,
                modifier = Modifier.padding(16.dp)
            )

            when (uiState) {
                is BookUiState.Loading -> LoadingView()
                is BookUiState.Error -> ErrorView(message = uiState.message, onRetry = onSearch)
                is BookUiState.Success -> {
                    if (uiState.books.isEmpty()) {
                        EmptyView()
                    } else {
                        BookList(books = uiState.books, onBookClick = onBookClick)
                    }
                }
            }
        }
    }
}

@Composable
private fun BookList(books: List<Book>, onBookClick: (Int) -> Unit) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        itemsIndexed(books) { index, book ->
            BookItemCard(book = book, onClick = { onBookClick(index) })
        }
    }
}