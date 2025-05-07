package com.android.monir.domain.repository

import androidx.paging.PagingData
import com.android.monir.domain.model.Product
import kotlinx.coroutines.flow.Flow


interface ProductRepository {
  suspend fun getProducts(): Flow<PagingData<Product>>
}
