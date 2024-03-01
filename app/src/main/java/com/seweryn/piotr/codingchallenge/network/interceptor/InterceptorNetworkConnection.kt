package com.seweryn.piotr.codingchallenge.network.interceptor

import com.seweryn.piotr.codingchallenge.network.NetworkConnectionManager
import com.seweryn.piotr.codingchallenge.network.exception.NetworkConnectionException
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class InterceptorNetworkConnection @Inject constructor(
  private val networkConnectionManager: NetworkConnectionManager,
) : Interceptor {

  override fun intercept(chain: Interceptor.Chain): Response {
    if (!networkConnectionManager.hasNetworkConnection()) {
      throw NetworkConnectionException()
    }
    return chain.proceed(
      chain.request().newBuilder().build(),
    )
  }

}