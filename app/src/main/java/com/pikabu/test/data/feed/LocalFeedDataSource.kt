package com.pikabu.test.data.feed

import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.pikabu.test.ui.feed.FeedData
import java.lang.reflect.Type


interface LocalFeedDataSource {
    fun loadFeeds(): List<FeedData>
    fun actionWithFeed(feedData: FeedData): FeedData
    fun removeFeed(id: Long)
}

class DefaultLocalMusicDataSource(
    private val gson: Gson,
    private val sharedPreferences: SharedPreferences
) : LocalFeedDataSource {

    companion object {
        private const val MY_FEEDS = "my_feeds"
    }

    override fun loadFeeds(): List<FeedData> {
        val cachedJson = sharedPreferences.getString(MY_FEEDS, "")
        return if (cachedJson.isNullOrBlank()) emptyList()
        else {
            val listType: Type = object : TypeToken<List<FeedData>>() {}.type
            gson.fromJson<List<FeedData>>(cachedJson, listType)
        }

    }

    override fun actionWithFeed(feedData: FeedData): FeedData {
        val list = loadFeeds().toMutableList()

        if (list.isNotEmpty()) {
            val index = list.indexOfFirst { it.id == feedData.id }
            if (index > -1) list.removeAt(index)
            else list.add(feedData.copy(isFav = true))
            saveFeeds(list)
        } else saveFeeds(listOf(feedData.copy(isFav = true)))

        return list.find { it.id == feedData.id } ?: feedData.copy(isFav = false)
    }

    override fun removeFeed(id: Long) {
        val list = loadFeeds().toMutableList()
        if (list.isNotEmpty()) {
            val index = list.indexOfFirst { it.id == id }
            list.removeAt(index)
            saveFeeds(list)
        }
    }


    private fun saveFeeds(list: List<FeedData>) {
        val strList = gson.toJson(list)
        sharedPreferences.edit().putString(MY_FEEDS, strList).apply()

    }


}
