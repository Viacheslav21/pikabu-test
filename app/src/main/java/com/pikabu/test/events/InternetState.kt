package com.pikabu.test.events

import androidx.lifecycle.LiveData

interface InternetStateUpdatable {
    fun updateInternetAvailability(isAvailable: Boolean)
}

interface InternetStateObservable {
    val isInternetAvailable: LiveData<Boolean>
}