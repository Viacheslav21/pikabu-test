package com.pikabu.test.di

import android.app.Activity
import com.google.gson.GsonBuilder
import com.pikabu.test.R
import com.pikabu.test.data.feed.*
import com.pikabu.test.data.network.RetrofitManager
import com.pikabu.test.events.InternetStateObservable
import com.pikabu.test.events.InternetStateUpdatable
import com.pikabu.test.events.SystemEventHolder
import com.pikabu.test.ui.feed.FeedsViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


private const val SHARED_PREF_NAME = "pikabu_sharedpref"


val systemModule = module {
    single { RetrofitManager(androidContext().getString(R.string.base_url)) }
    single { GsonBuilder().create() }
    single { androidContext().getSharedPreferences("preferences", Activity.MODE_PRIVATE) }

    single<InternetStateObservable> { SystemEventHolder }
    single<InternetStateUpdatable> { SystemEventHolder }
}


val feedModule = module {
    single<LocalFeedDataSource> {
        val sPref = androidContext().getSharedPreferences(SHARED_PREF_NAME, Activity.MODE_PRIVATE)
        DefaultLocalMusicDataSource(get(), sPref)
    }

    single { get<RetrofitManager>().getService(FeedApi::class.java) }
    single<RemoteFeedDataSource> { DefaultRemoteFeedDataSource(get()) }


    single<FeedRepository> { DefaultFeedRepository(get(), get()) }
    viewModel { FeedsViewModel(get()) }
}

