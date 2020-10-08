package com.pikabu.test.data.network

import android.content.Context
import android.net.ConnectivityManager
import android.os.Build

@Suppress("DEPRECATION")
 fun getConnectionState(context: Context): Boolean {
    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetInfo = connectivityManager.activeNetworkInfo
    val mobNetInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)

    return activeNetInfo?.isConnected == true || mobNetInfo?.isConnected == true
}