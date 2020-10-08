package com.pikabu.test.ui.feed

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pikabu.test.R

class FeedAdapter(
    private val onFeedClick: (FeedData, ClickAction) -> Unit,
) : RecyclerView.Adapter<FeedViewHolder>() {

    private val feedList: MutableList<FeedData> = mutableListOf()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_feed, parent, false)
        return FeedViewHolder(view, ::handleItemClick)
    }

    private fun handleItemClick(position: Int, clickAction: ClickAction) {
        onFeedClick(feedList[position], clickAction)
    }


    override fun getItemCount(): Int = feedList.size

    override fun onBindViewHolder(holder: FeedViewHolder, position: Int) {
        holder.bind(feedList[position])
    }


    fun setData(data: List<FeedData>) {
        feedList.clear()
        feedList.addAll(data)
        notifyDataSetChanged()
    }

    fun removeFeed(feedData: FeedData) {
        val index = feedList.indexOfFirst { it.id == feedData.id }
        if (index > -1) {
            feedList.removeAt(index)
            notifyItemRemoved(index)
        }
    }

    fun updateFeed(feedData: FeedData) {
        val position = feedList.indexOfFirst { it.id == feedData.id }
        if (position > -1) feedList[position] = feedData

    }


}
