package com.android.monir.data.remote

import com.android.monir.data.remote.dto.ProductResponseDto
import retrofit2.http.GET
import retrofit2.http.Query


interface ProductApi {

  @GET("/products")
  suspend fun getUsers(@Query("limit") limit: Int, @Query("skip") skip: Int): ProductResponseDto

  companion object {
    const val BASE_URL = "https://dummyjson.com"
  }
}
