package com.android.monir.di

import com.android.monir.data.remote.ProductApi
import com.android.monir.data.repository.ProductRepositoryImpl
import com.android.monir.domain.repository.ProductRepository
import com.android.monir.domain.usecase.GetProductsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton
import kotlin.jvm.java

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

  @Provides
  @Singleton
  fun provideLoggingInterceptor() = HttpLoggingInterceptor().apply {
    level = HttpLoggingInterceptor.Level.BODY
  }

  @Provides
  @Singleton
  fun provideOkHttpClient(
    authInterceptor: Interceptor, loggingInterceptor: HttpLoggingInterceptor
  ): OkHttpClient =
    OkHttpClient.Builder().addInterceptor(authInterceptor).addInterceptor(loggingInterceptor)
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
