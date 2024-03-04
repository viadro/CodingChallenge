package com.seweryn.piotr.codingchallenge.network.interceptor

import com.seweryn.piotr.codingchallenge.BuildConfig
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthenticationInterceptor @Inject constructor() : Interceptor {

  private companion object {
    const val API_KEY_PARAMETER_NAME = "apiKey"
  }

  override fun intercept(chain: Interceptor.Chain): Response =
    chain.proceed(
      chain.request().run {
        newBuilder().url(
          url.createNewUrl()
        ).build()
      }
    )

  private fun HttpUrl.createNewUrl() =
    newBuilder()
      .addQueryParameter(API_KEY_PARAMETER_NAME, BuildConfig.API_KEY)
      .build()
}