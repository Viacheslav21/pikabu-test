package com.pikabu.test.data.feed


import com.pikabu.test.data.model.FeedDataDTO
import retrofit2.http.GET

interface FeedApi {
    @GET("feed.php")
    suspend fun loadFeeds(): List<FeedDataDTO>

}
