package com.example.data.db

import androidx.lifecycle.LiveData
import com.example.data.dto.Vacancy
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class VacancyRepository @Inject constructor(private val vacancyDao: VacancyDao) {

    val allVacancies: LiveData<List<VacancyEntity>> = vacancyDao.getAllVacancies()

    val favoriteVacancies: LiveData<List<VacancyEntity>> = vacancyDao.getFavoriteVacancies()

    fun getFavoriteVacanciesCount(): LiveData<Int> = vacancyDao.getFavoriteVacanciesCount()

    suspend fun addVacancies(vacancies: List<Vacancy>) {
        val vacancyEntities = vacancies.map { vacancy ->
            VacancyEntity(
                id = vacancy.id,
                lookingNumber = vacancy.lookingNumber,
                title = vacancy.title,
                town = vacancy.address.town,
                street = vacancy.address.street,
                house = vacancy.address.house,
                company = vacancy.company,
                experiencePreview = vacancy.experience.previewText,
                experienceText = vacancy.experience.text,
                publishedDate = vacancy.publishedDate,
                isFavorite = vacancy.isFavorite,
                salaryFull = vacancy.salary.full,
                salaryShort = vacancy.salary.short,
                appliedNumber = vacancy.appliedNumber,
                responsibilities = vacancy.responsibilities
            )
        }
        vacancyDao.insertVacancies(vacancyEntities)
    }

    suspend fun updateVacancy(vacancy: VacancyEntity) {
        vacancyDao.updateVacancy(vacancy.copy(isFavorite = !vacancy.isFavorite))
    }
}