package com.android.monir.presentation.productlist

sealed class ProductListEvent {
  object Refresh: ProductListEvent()
}