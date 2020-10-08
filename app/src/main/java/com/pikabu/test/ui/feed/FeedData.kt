package com.pikabu.test.ui.feed

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FeedData(
    val id: Long,
    val title: String,
    val body: String,
    val images: List<String>,
    val isFav: Boolean = false
) : Parcelable
