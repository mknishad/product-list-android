package com.android.monir.domain.model

import com.android.monir.data.remote.dto.DimensionsDto
import com.android.monir.data.remote.dto.MetaDto
import com.android.monir.data.remote.dto.ReviewDto

data class Product(
  val availabilityStatus: String,
  val category: String,
  val discountPercentage: Double,
  val id: Int,
  val images: List<String>,
  val price: Double,
  val rating: Double,
  val stock: Int,
  val thumbnail: String,
  val title: String,
)