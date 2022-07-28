package com.example.movies.dto

import com.google.gson.annotations.SerializedName

data class Movie(
    @SerializedName("display_title")
    val displayTitle: String,
    @SerializedName("summary_short")
    val summaryShort: String,
    val link: Link,
    val multimedia: MultiMedia
)

class Results<T>(
    val results: T
)

data class MultiMedia(
    val src: String
)

data class Link(
    val url: String
)