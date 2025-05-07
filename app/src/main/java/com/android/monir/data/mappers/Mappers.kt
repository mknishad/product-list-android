package com.android.monir.data.mappers

import com.android.monir.data.remote.dto.ProductDto
import com.android.monir.domain.model.Product

fun ProductDto.toProduct(): Product {
  return Product(
    availabilityStatus = availabilityStatus,
    category = category,
    discountPercentage = discountPercentage,
    id = id,
    price = price,
    thumbnail = thumbnail,
    rating = rating,
    stock = stock,
    title = title
  )
}