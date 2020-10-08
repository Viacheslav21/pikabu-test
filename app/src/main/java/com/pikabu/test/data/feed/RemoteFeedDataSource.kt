package com.pikabu.test.data.feed

import com.pikabu.test.data.model.FeedDataDTO


interface RemoteFeedDataSource {
   suspend fun loadFeeds(): List<FeedDataDTO>

}

class DefaultRemoteFeedDataSource(private val api: FeedApi ) : RemoteFeedDataSource {
    override suspend fun loadFeeds(): List<FeedDataDTO> = api.loadFeeds()
}