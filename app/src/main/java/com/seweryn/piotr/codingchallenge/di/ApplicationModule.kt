package com.seweryn.piotr.codingchallenge.di

import android.content.Context
import androidx.room.Room
import com.seweryn.piotr.codingchallenge.data.api.ImagesApi
import com.seweryn.piotr.codingchallenge.data.database.ImagesDao
import com.seweryn.piotr.codingchallenge.data.database.ImagesDatabase
import com.seweryn.piotr.codingchallenge.data.repository.ImagesRepositoryImpl
import com.seweryn.piotr.codingchallenge.domain.repository.ImagesRepository
import com.seweryn.piotr.codingchallenge.domain.usecase.GetImagesUseCase
import com.seweryn.piotr.codingchallenge.domain.usecase.GetImagesUseCaseImpl
import com.seweryn.piotr.codingchallenge.domain.usecase.GetSavedImageUseCase
import com.seweryn.piotr.codingchallenge.domain.usecase.GetSavedImageUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApplicationModule {

  @Provides
  @Singleton
  fun provideContext(
    @ApplicationContext context: Context,
  ): Context = context

  @Provides
  @Singleton
  fun provideImagesRepository(
    api: ImagesApi,
    imagesDao: ImagesDao,
  ): ImagesRepository =
    ImagesRepositoryImpl(
      api = api,
      imagesDao = imagesDao,
    )

  @Singleton
  @Provides
  internal fun provideDatabase(
    @ApplicationContext context: Context,
  ): ImagesDatabase =
    Room.databaseBuilder(
      context,
      ImagesDatabase::class.java,
      ImagesDatabase.NAME,
    ).build()

  @Provides
  internal fun provideDocumentsConfigsDao(database: ImagesDatabase): ImagesDao =
    database.imagesDao

  @Provides
  @Singleton
  fun provideGetImagesUseCase(
    imagesRepository: ImagesRepository,
  ): GetImagesUseCase = GetImagesUseCaseImpl(
    repository = imagesRepository,
  )

  @Provides
  @Singleton
  fun provideGetSavedImageUseCase(
    imagesRepository: ImagesRepository,
  ): GetSavedImageUseCase = GetSavedImageUseCaseImpl(
    repository = imagesRepository,
  )
}