package com.android.monir.di

import android.content.Context
import com.android.monir.data.remote.CacheInterceptor
import com.android.monir.data.remote.ProductApi
import com.android.monir.data.repository.ProductRepositoryImpl
import com.android.monir.domain.repository.ProductRepository
import com.android.monir.domain.usecase.GetProductsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

  @Provides
  @Singleton
  fun provideCacheInterceptor(): CacheInterceptor {
    return CacheInterceptor()
  }

  @Provides
  @Singleton
  fun provideLoggingInterceptor() = HttpLoggingInterceptor().apply {
    level = HttpLoggingInterceptor.Level.BODY
  }

  @Provides
  @Singleton
  fun provideCache(@ApplicationContext context: Context): Cache {
    val httpCacheDirectory = File(context.cacheDir, "http-cache")
    val cacheSize = 10 * 1024 * 1024 // 10 MiB
    val cache = Cache(httpCacheDirectory, cacheSize.toLong())
    return cache
  }

  @Provides
  @Singleton
  fun provideOkHttpClient(
    cacheInterceptor: CacheInterceptor,
    cache: Cache,
    loggingInterceptor: HttpLoggingInterceptor
  ): OkHttpClient =
    OkHttpClient.Builder()
      .addNetworkInterceptor(cacheInterceptor)
      .cache(cache)
      .addInterceptor(loggingInterceptor)
      .build()

  @Provides
  @Singleton
  fun provideProductApi(okHttpClient: OkHttpClient): ProductApi {
    return Retrofit.Builder().baseUrl(ProductApi.BASE_URL)
      .addConverterFactory(GsonConverterFactory.create()).client(okHttpClient).build()
      .create(ProductApi::class.java)
  }

  @Provides
  @Singleton
  fun provideProductRepository(api: ProductApi): ProductRepository {
    return ProductRepositoryImpl(api)
  }

  @Provides
  @Singleton
  fun provideGetProductUseCase(repository: ProductRepository): GetProductsUseCase {
    return GetProductsUseCase(repository)
  }
}
