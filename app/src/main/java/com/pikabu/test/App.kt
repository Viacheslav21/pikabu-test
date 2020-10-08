package com.pikabu.test

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import com.pikabu.test.di.connectedModules
import com.pikabu.test.events.InternetStateUpdatable
import org.koin.android.ext.android.get
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            modules(connectedModules)
        }

        registerBroadcasts()

    }

    private fun registerBroadcasts() {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        connectivityManager.registerNetworkCallback(
            NetworkRequest.Builder()
                .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
                .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
                .addTransportType(NetworkCapabilities.TRANSPORT_VPN)
                .build(),
            NetworkStateCallback(get())
        )
    }

    class NetworkStateCallback(private val internetState: InternetStateUpdatable) : ConnectivityManager.NetworkCallback() {
        override fun onLost(network: Network?) = internetState.updateInternetAvailability(false)
        override fun onUnavailable() = internetState.updateInternetAvailability(false)
        override fun onAvailable(network: Network?) = internetState.updateInternetAvailability(true)
    }

}