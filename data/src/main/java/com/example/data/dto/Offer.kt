package com.example.data.dto

data class Offer(
    val id: String?,
    val title: String,
    val link: String,
    val button: Button? = null
)
