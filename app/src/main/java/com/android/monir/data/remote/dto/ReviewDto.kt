package com.android.monir.data.remote.dto

data class ReviewDto(
    val comment: String,
    val date: String,
    val rating: Int,
    val reviewerEmail: String,
    val reviewerName: String
)