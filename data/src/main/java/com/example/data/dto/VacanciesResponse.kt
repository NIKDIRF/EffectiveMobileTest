package com.example.data.dto

data class VacanciesResponse(
    val offers: List<Offer>,
    val vacancies: List<Vacancy>
)