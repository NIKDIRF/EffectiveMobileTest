package com.example.data.dto

import com.example.data.model.DisplayableItem

data class Vacancy(
    val id: String,
    val lookingNumber: Int? = null,
    val title: String,
    val address: com.example.data.dto.Address,
    val company: String,
    val experience: Experience,
    val publishedDate: String,
    val isFavorite: Boolean,
    val salary: Salary,
    val schedules: List<String>,
    val appliedNumber: Int? = null,
    val description: String,
    val responsibilities: String,
    val questions: List<String>
) : DisplayableItem
