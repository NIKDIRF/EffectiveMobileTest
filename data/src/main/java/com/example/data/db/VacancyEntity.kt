package com.example.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.data.model.DisplayableItem

@Entity(tableName = "vacancies")
data class VacancyEntity(
    @PrimaryKey val id: String,
    val lookingNumber: Int? = null,
    val title: String,
    val town: String,
    val street: String,
    val house: String,
    val company: String,
    val experiencePreview: String,
    val experienceText: String,
    val publishedDate: String,
    val isFavorite: Boolean,
    val salaryFull: String,
    val salaryShort: String? = null,
    val appliedNumber: Int? = null,
    val responsibilities: String
): DisplayableItem