package com.android.monir.data.remote.dto

data class ProductResponseDto(
    val limit: Int,
    val products: List<ProductDto>,
    val skip: Int,
    val total: Int
)