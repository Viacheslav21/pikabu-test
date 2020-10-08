package com.pikabu.test.data.feed

import com.pikabu.test.data.model.toDomain
import com.pikabu.test.ui.feed.FeedData
import com.pikabu.test.ui.feed.FeedsDisplayType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

interface FeedRepository {
    suspend fun loadData(type: FeedsDisplayType): List<FeedData>
    suspend fun actionWithFeed(feedData: FeedData) : FeedData
    suspend fun removeFeed(id: Long)
}

class DefaultFeedRepository(
    private val localFeedDataSource: LocalFeedDataSource,
    private val remoteFeedDataSource: RemoteFeedDataSource
) : FeedRepository {


    override suspend fun loadData(type: FeedsDisplayType): List<FeedData> {

        return withContext(Dispatchers.IO) {
            when (type) {
                FeedsDisplayType.FEEDS -> loadRemoteFeed()
                FeedsDisplayType.FAV -> localFeedDataSource.loadFeeds()
            }
        }

    }

    private suspend fun loadRemoteFeed(): List<FeedData> {
        val remoteList = remoteFeedDataSource.loadFeeds().toDomain()
        val localList = localFeedDataSource.loadFeeds()
        return (remoteList + localList)
            .groupBy { it.id }
            .entries
            .map {
                val list = it.value.toMutableList()
                if (list.size > 1) {
                    val feed = list[0]
                    list.clear()
                    list.add(feed.copy(isFav = true))
                }
                list
            }
            .toList()
            .flatten()

    }

    override suspend fun actionWithFeed(feedData: FeedData): FeedData = withContext(Dispatchers.IO) {
        localFeedDataSource.actionWithFeed(feedData)
    }

    override suspend fun removeFeed(id: Long)= withContext(Dispatchers.IO) {
        localFeedDataSource.removeFeed(id)
    }

}

