package com.pemmob.responsi.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.pemmob.responsi.R
import com.pemmob.responsi.data.model.Book
import com.pemmob.responsi.ui.component.InfoRow
import com.pemmob.responsi.ui.viewmodel.BookUiState
import com.pemmob.responsi.ui.viewmodel.BookViewModel
import com.pemmob.responsi.util.authorsText
import com.pemmob.responsi.util.editionText
import com.pemmob.responsi.util.languageText
import com.pemmob.responsi.util.titleOrDefault
import com.pemmob.responsi.util.yearText

// ============ STATEFUL ============
@Composable
fun DetailScreen(
    bookIndex: Int,
    viewModel: BookViewModel,
    onBackClick: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    // Tanpa API call kedua: ambil dari list yang sudah ada di ViewModel
    val book = (uiState as? BookUiState.Success)?.books?.getOrNull(bookIndex)

    StatelessDetailScreen(book = book, onBackClick = onBackClick)
}

// ============ STATELESS ============
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StatelessDetailScreen(book: Book?, onBackClick: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detail Buku") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
                ),
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            painter = painterResource(id = R.drawable.back_icon),
                            contentDescription = "Kembali"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        if (book == null) {
            Box(
                modifier = Modifier.fillMaxSize().padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Text("Data buku tidak ditemukan.")
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Text(
                    text = book.titleOrDefault(),
                    style = MaterialTheme.typography.headlineMedium
                )

                Card(
                    modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    ),
                    elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        InfoRow(label = "Penulis", value = book.authorsText())
                        HorizontalDivider()
                        InfoRow(label = "Tahun Terbit Pertama", value = book.yearText())
                        HorizontalDivider()
                        InfoRow(label = "Jumlah Edisi", value = book.editionText())
                        HorizontalDivider()
                        InfoRow(label = "Bahasa", value = book.languageText())
                    }
                }
            }
        }
    }
}