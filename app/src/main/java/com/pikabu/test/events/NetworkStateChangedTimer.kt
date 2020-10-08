package com.pikabu.test.events

import androidx.lifecycle.MutableLiveData
import android.os.CountDownTimer

class NetworkStateChangedTimer(private val liveData: MutableLiveData<Boolean>) : CountDownTimer(
    SWITCHING_TIMEOUT, SWITCHING_TIMEOUT
) {

    companion object {
        private const val SWITCHING_TIMEOUT = 2_000L
    }

    private var value = true

    override fun onTick(millisUntilFinished: Long) = Unit
    override fun onFinish() = liveData.postValue(value)

    fun updateWithPause(isAvailable: Boolean) {
        value = isAvailable
        if (value) onFinish()
        else start()
    }
}
