package com.seweryn.piotr.codingchallenge.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

interface NetworkConnectionManager {
  fun hasNetworkConnection(): Boolean
}

class NetworkConnectionManagerImpl(
  private val context: Context,
) : NetworkConnectionManager {

  override fun hasNetworkConnection(): Boolean {
    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val network = connectivityManager.activeNetwork ?: return false
    val networkCapabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
    return when {
      networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
      networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
      networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
      networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_LOWPAN) -> true
      else -> false
    }
  }

}