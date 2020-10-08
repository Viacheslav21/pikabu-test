package com.pikabu.test.events

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.pikabu.test.data.network.getConnectionState
import org.koin.core.KoinComponent
import org.koin.core.inject

class NetworkStateReceiver : BroadcastReceiver(), KoinComponent {

    private val internetState: InternetStateUpdatable by inject()

    override fun onReceive(context: Context?, intent: Intent?) {
        val isAvailable = context?.let { getConnectionState(it) } ?: false
        internetState.updateInternetAvailability(isAvailable)
    }
}