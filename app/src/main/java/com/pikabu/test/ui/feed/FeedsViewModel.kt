package com.pikabu.test.ui.feed

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pikabu.test.data.feed.FeedRepository
import kotlinx.coroutines.launch

class FeedsViewModel(private val feedRepository: FeedRepository) : ViewModel() {

    private val _feedList = MutableLiveData<List<FeedData>>()
    val feedList: LiveData<List<FeedData>> = _feedList

    private val _actionWithFeed = MutableLiveData<FeedData>()
    val actionWithFeed: LiveData<FeedData> = _actionWithFeed

    fun loadFeeds(type: FeedsDisplayType) {
        viewModelScope.launch {
            try {
                val list = feedRepository.loadData(type)
                _feedList.postValue(list)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun actionWithFeed(feedData: FeedData) {
        viewModelScope.launch {
            try {
                val feed = feedRepository.actionWithFeed(feedData)
                _actionWithFeed.postValue(feed)
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
    }

    fun removeFeed(id: Long) {
        viewModelScope.launch {
            try {
               feedRepository.removeFeed(id)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }


}