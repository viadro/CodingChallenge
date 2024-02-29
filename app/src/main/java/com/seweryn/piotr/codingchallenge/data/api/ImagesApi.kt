package com.seweryn.piotr.codingchallenge.data.api

import com.seweryn.piotr.codingchallenge.data.model.ImagesListResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ImagesApi {
  @GET("api/")
  suspend fun getImages(
    @Query("q") query: String
  ): Response<ImagesListResponse>
}