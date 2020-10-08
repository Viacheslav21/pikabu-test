package com.pikabu.test.ui.feed

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.pikabu.test.R
import kotlinx.android.synthetic.main.item_feed.view.*


enum class ClickAction {
    ITEM_CLICK, FAV_CLICK
}

class FeedViewHolder(
    itemView: View,
    onItemClick: (Int, ClickAction) -> Unit
) : RecyclerView.ViewHolder(itemView) {

    init {
        itemView.setOnClickListener { onItemClick(adapterPosition, ClickAction.ITEM_CLICK) }
        itemView.feed_fav.setOnClickListener {
            it.isSelected = !it.isSelected
            onItemClick(adapterPosition, ClickAction.FAV_CLICK)
        }

    }

    fun bind(feed: FeedData) {
        itemView.feed_fav.isSelected = feed.isFav
        itemView.feed_title.text = feed.title
        itemView.feed_body.text = feed.body
        val image = feed.images.takeIf { it.isNotEmpty() }?.first() ?: R.drawable.no_image
        Glide.with(itemView).load(image).into(itemView.feed_image)
    }


}