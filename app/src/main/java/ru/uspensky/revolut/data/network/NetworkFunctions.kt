package ru.uspensky.revolut.data.network

import android.content.Context
import android.net.ConnectivityManager
import javax.inject.Inject

/**
 * Created by Dmitry Uspensky on 26/10/2018.
 */
class NetworkFunctions @Inject constructor(private val context: Context) {
    fun isNetworkConnected(): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = cm.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }
}