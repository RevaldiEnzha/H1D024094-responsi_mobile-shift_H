package com.pemmob.responsi.util

import com.pemmob.responsi.data.model.Book

private val languageNames = mapOf(
    "eng" to "Inggris", "ind" to "Indonesia", "may" to "Melayu",
    "jav" to "Jawa", "dut" to "Belanda", "fre" to "Prancis",
    "ger" to "Jerman", "spa" to "Spanyol", "ita" to "Italia",
    "por" to "Portugis", "rus" to "Rusia", "jpn" to "Jepang",
    "chi" to "Mandarin", "ara" to "Arab"
)

fun Book.titleOrDefault(): String = title ?: "Tanpa judul"

fun Book.authorsText(): String =
    authorName?.takeIf { it.isNotEmpty() }?.joinToString(", ") ?: "Penulis tidak diketahui"

fun Book.yearText(): String = firstPublishYear?.toString() ?: "-"

fun Book.editionText(): String = editionCount?.let { "$it edisi" } ?: "-"

fun Book.languageText(): String =
    language
        ?.distinct()
        ?.take(10)
        ?.joinToString(", ") { languageNames[it] ?: it.uppercase() }
        ?.takeIf { it.isNotBlank() }
        ?: "-"