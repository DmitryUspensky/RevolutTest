package ru.uspensky.revolut.data.network

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import java.util.*

/**
 * Created by Dmitry Uspensky on 26/10/2018.
 */
class ConnectivityStatusReceiver : BroadcastReceiver() {

    private val listeners = LinkedList<IConnectivityCallback>()

    override fun onReceive(context: Context?, intent: Intent?) {
        if (!isInitialStickyBroadcast) {
            val conMan = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val netInfo = conMan.activeNetworkInfo
            if (netInfo != null) {
                notifyListeners(true)
            } else {
                notifyListeners(false)
            }
        }
    }

    fun registerListener(listener: IConnectivityCallback) {
        listeners.add(listener)
    }

    fun unRegisterListener(listener: IConnectivityCallback) {
        listeners.remove(listener)
    }

    private fun notifyListeners(connectPresent: Boolean) {
        for (listener in listeners) {
            listener.onConnectivityStateChanged(connectPresent)
        }
    }

    interface IConnectivityCallback {
        fun onConnectivityStateChanged(connectPresent: Boolean)
    }
}