package com.pikabu.test.ui.feed

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.pikabu.test.R
import kotlinx.android.synthetic.main.feed_fragmnet.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class FeedFragment : Fragment(R.layout.feed_fragmnet) {

    private val adapter = FeedAdapter(::onItemClicked)

    private val viewModel by viewModel<FeedsViewModel>()

    private val args: FeedFragmentArgs by navArgs()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initEventSubscriptions()
        viewModel.loadFeeds(args.type)
    }

    private fun initEventSubscriptions() {
        viewModel.feedList.observe(viewLifecycleOwner, Observer(::initFeedList))
        viewModel.actionWithFeed.observe(viewLifecycleOwner, Observer(::actionWithFeed))
    }

    private fun actionWithFeed(feedData: FeedData) {
        when (args.type) {
            FeedsDisplayType.FAV -> adapter.removeFeed(feedData)
            FeedsDisplayType.FEEDS -> adapter.updateFeed(feedData)
        }
    }

    private fun initFeedList(musicList: List<FeedData>) {
        feed_no_data.isVisible = musicList.isEmpty()
        feedList.layoutManager = LinearLayoutManager(requireContext())
        feedList.adapter = adapter
        adapter.setData(musicList)
    }

    private fun onItemClicked(feed: FeedData, clickAction: ClickAction) {
        when (clickAction) {
            ClickAction.ITEM_CLICK -> navigateToFeedDetail(feed)
            ClickAction.FAV_CLICK -> onFavClick(feed)
        }

    }

    private fun onFavClick(feedData: FeedData) {
        when (args.type) {
            FeedsDisplayType.FAV -> removeFeed(feedData)
            FeedsDisplayType.FEEDS -> viewModel.actionWithFeed(feedData)
        }
    }

    private fun removeFeed(feedData: FeedData) {
        viewModel.removeFeed(feedData.id)
        adapter.removeFeed(feedData)
    }


    private fun navigateToFeedDetail(feed: FeedData) = findNavController().navigate(
        FeedFragmentDirections.actionRecordVideoScreenToSelectMusicScreen(feed)
    )

}



