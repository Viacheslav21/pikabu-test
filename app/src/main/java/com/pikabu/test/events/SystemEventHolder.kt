package com.pikabu.test.events

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.pikabu.test.data.network.getConnectionState
import org.koin.core.KoinComponent
import org.koin.core.get

object SystemEventHolder: KoinComponent, InternetStateObservable, InternetStateUpdatable {

    private val mutableIsInternetAvailable = MutableLiveData<Boolean>()
        .apply { postValue(getConnectionState(get())) }

    private val timer = NetworkStateChangedTimer(mutableIsInternetAvailable)

    override val isInternetAvailable: LiveData<Boolean> = mutableIsInternetAvailable

    override fun updateInternetAvailability(isAvailable: Boolean) {
        if (isAvailable) {
            timer.cancel()
            mutableIsInternetAvailable.postValue(isAvailable)
        } else
            timer.updateWithPause(isAvailable || getConnectionState(get()))
    }

}