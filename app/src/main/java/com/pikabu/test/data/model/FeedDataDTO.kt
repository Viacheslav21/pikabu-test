package com.pikabu.test.data.model

import com.pikabu.test.ui.feed.FeedData

data class FeedDataDTO(
    val id: Long,
    val title: String,
    val body: String?,
    val images: List<String>?
)

fun FeedDataDTO.toDomain() = FeedData(
    id,
    title,
    body.orEmpty(),
    images?.filterNot { it.isNullOrBlank() } ?: emptyList())

fun List<FeedDataDTO>.toDomain(): List<FeedData> = map { it.toDomain() }
