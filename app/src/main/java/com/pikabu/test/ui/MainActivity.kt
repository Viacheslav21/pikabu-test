package com.pikabu.test.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.pikabu.test.ui.internet.NoInternetFragment
import com.pikabu.test.MainGraphDirections
import com.pikabu.test.R
import com.pikabu.test.events.InternetStateObservable
import com.pikabu.test.ui.feed.FeedsDisplayType
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.inject


class MainActivity : AppCompatActivity() {

    private val internetState: InternetStateObservable by inject()

    private var isNetworkAvailable = true


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        feeds.setOnClickListener { navigateToFeedsWithType(FeedsDisplayType.FEEDS) }
        feeds_fav.setOnClickListener { navigateToFeedsWithType(FeedsDisplayType.FAV) }


        findNavController(R.id.nav_main_fragment).addOnDestinationChangedListener { _, destination, _ ->
            hideBottomBar(destination.id != R.id.feedDetailFragment)
        }

        internetState.isInternetAvailable.observe(this, Observer { isAvailable ->
            isNetworkAvailable = isAvailable
            if (isAvailable) onConnectionAvailable()
            else onConnectionUnavailable()
            hideBottomBar(isAvailable)

        })

    }

    private fun onConnectionAvailable() =
        findNavController(R.id.nav_main_fragment).takeIf { checkCurrentFragment<NoInternetFragment>() }
            ?.navigateUp()

    private fun onConnectionUnavailable() =
        findNavController(R.id.nav_main_fragment).navigate(R.id.noInternetFragment)


    private fun hideBottomBar(isVisible: Boolean) {
        mainBottomNavigation.isVisible = isVisible
    }

    private fun navigateToFeedsWithType(type: FeedsDisplayType) {
        findNavController(R.id.nav_main_fragment).navigate(
            MainGraphDirections.navigateToFeedWithAction(
                type
            )
        )
    }

    private inline fun <reified T> checkCurrentFragment(): Boolean {
        return nav_main_fragment.childFragmentManager.fragments.takeIf { it.isNotEmpty() }
            ?.last() is T
    }


    override fun onBackPressed() {
        if (checkCurrentFragment<NoInternetFragment>()) finish()
        else super.onBackPressed()

    }
}