package com.android.monir.presentation.productlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.android.monir.domain.model.Product
import com.android.monir.domain.usecase.GetProductsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductListViewModel @Inject constructor(private val getProductsUseCase: GetProductsUseCase) :
  ViewModel() {

  private val _productListState: MutableStateFlow<PagingData<Product>> =
    MutableStateFlow(value = PagingData.empty())
  val productListState: StateFlow<PagingData<Product>> = _productListState

  init {
    onEvent(ProductListEvent.Refresh)
  }

  fun onEvent(event: ProductListEvent) {
    when (event) {
      is ProductListEvent.Refresh -> {
        viewModelScope.launch {
          getProducts()
        }
      }
    }
  }

  private suspend fun getProducts() {
    getProductsUseCase.invoke()
      .distinctUntilChanged()
      .cachedIn(viewModelScope)
      .collect {
        _productListState.value = it
      }
  }
}
