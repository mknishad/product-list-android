package com.android.monir.presentation.productlist

sealed class ProductListEvent {
  object Retry: ProductListEvent()
  object PullToRefresh: ProductListEvent()
}