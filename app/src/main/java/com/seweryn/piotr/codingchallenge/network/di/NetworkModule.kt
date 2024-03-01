package com.seweryn.piotr.codingchallenge.network.di

import android.content.Context
import com.seweryn.piotr.codingchallenge.network.NetworkConnectionManager
import com.seweryn.piotr.codingchallenge.network.NetworkConnectionManagerImpl
import com.seweryn.piotr.codingchallenge.network.interceptor.InterceptorNetworkConnection
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Converter
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

  @Singleton
  @Provides
  fun provideNetworkConnectionManager(
    context: Context,
  ): NetworkConnectionManager = NetworkConnectionManagerImpl(
    context = context
  )

  @Singleton
  @Provides
  fun provideOkHttpClient(
    interceptorNetworkConnection: InterceptorNetworkConnection
  ): OkHttpClient = OkHttpClient.Builder()
    .addInterceptor(interceptorNetworkConnection)
    .build()
}