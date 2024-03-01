package com.seweryn.piotr.codingchallenge.network.di

import android.content.Context
import com.seweryn.piotr.codingchallenge.BuildConfig
import com.seweryn.piotr.codingchallenge.data.api.ImagesApi
import com.seweryn.piotr.codingchallenge.network.NetworkConnectionManager
import com.seweryn.piotr.codingchallenge.network.NetworkConnectionManagerImpl
import com.seweryn.piotr.codingchallenge.network.interceptor.AuthenticationInterceptor
import com.seweryn.piotr.codingchallenge.network.interceptor.NetworkConnectionInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Converter.Factory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
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
    networkConnectionInterceptor: NetworkConnectionInterceptor,
    authenticationInterceptor: AuthenticationInterceptor,
  ): OkHttpClient = OkHttpClient.Builder()
    .addInterceptor(networkConnectionInterceptor)
    .addInterceptor(authenticationInterceptor)
    .build()

  @Singleton
  @Provides
  fun provideConverterFactory(): Factory =
    GsonConverterFactory.create()

  @Singleton
  @Provides
  fun provideRetrofitClient(
    okHttpClient: OkHttpClient,
    converterFactory: Factory,
  ): Retrofit =
    Retrofit.Builder()
      .baseUrl(BuildConfig.API_URL)
      .client(okHttpClient)
      .addConverterFactory(converterFactory)
      .build()

  @Singleton
  @Provides
  fun provideImagesApi(
    retrofit: Retrofit,
  ): ImagesApi = retrofit.create(ImagesApi::class.java)
}