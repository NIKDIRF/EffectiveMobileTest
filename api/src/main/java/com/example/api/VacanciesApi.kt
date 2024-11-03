package com.example.api

import com.example.data.dto.VacanciesResponse
import retrofit2.Response
import retrofit2.http.GET

interface VacanciesApi {
    @GET("uc?id=1z4TbeDkbfXkvgpoJprXbN85uCcD7f00r&export=download")
    suspend fun getVacancies(): Response<VacanciesResponse>
}