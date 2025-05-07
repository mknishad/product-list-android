package com.android.monir.domain.usecase

import androidx.paging.PagingData
import com.android.monir.domain.model.Product
import com.android.monir.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow

class GetProductsUseCase(private val repository: ProductRepository) {
  suspend fun invoke(): Flow<PagingData<Product>> {
    return repository.getProducts()
  }
}