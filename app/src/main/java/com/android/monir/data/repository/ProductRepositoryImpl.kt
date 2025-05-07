package com.android.monir.data.repository

import android.app.appsearch.SearchResult
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.android.monir.data.remote.ProductApi
import com.android.monir.data.repository.paging.ProductPagingSource
import com.android.monir.domain.model.Product
import com.android.monir.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
  private val api: ProductApi
) : ProductRepository {

  override suspend fun getProducts(): Flow<PagingData<Product>> {
    return Pager(
      config = PagingConfig(pageSize = 20, prefetchDistance = 2),
      pagingSourceFactory = {
        ProductPagingSource(api)
      }
    ).flow
  }
}
