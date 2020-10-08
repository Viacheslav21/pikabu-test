package com.pikabu.test.ui.feed.detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.pikabu.test.R
import com.pikabu.test.ui.feed.FeedsViewModel
import com.pikabu.test.ui.feed.FeedData
import kotlinx.android.synthetic.main.item_feed.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class FeedDetailFragment : Fragment(R.layout.feed_detail_fragmnet) {

    private val args: FeedDetailFragmentArgs by navArgs()

    private val viewModel by viewModel<FeedsViewModel>()


    private var isSelected = false
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        isSelected = savedInstanceState?.getBoolean("isSelected") ?: args.feed.isFav
        initData(args.feed, isSelected)
    }

    private fun initData(feed: FeedData, selected: Boolean) {
        feed_fav.isSelected = selected
        feed_title.text = feed.title
        feed_body.text = feed.body
        val image = feed.images.takeIf { it.isNotEmpty() }?.first() ?: R.drawable.no_image
        Glide.with(requireContext()).load(image).into(feed_image)
        feed_fav.setOnClickListener {
            it.isSelected = !it.isSelected
            isSelected = it.isSelected
            viewModel.actionWithFeed(feed)
        }
    }

    override fun onSaveInstanceState(bundle: Bundle) {
        super.onSaveInstanceState(bundle)
        bundle.putBoolean("isSelected", isSelected)
    }


}


