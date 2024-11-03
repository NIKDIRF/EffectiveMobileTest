package com.example.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface VacancyDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertVacancies(vacancies: List<VacancyEntity>)

    @Query("SELECT * FROM vacancies")
    fun getAllVacancies(): LiveData<List<VacancyEntity>>

    @Query("SELECT * FROM vacancies WHERE isFavorite = 1")
    fun getFavoriteVacancies(): LiveData<List<VacancyEntity>>

    @Update
    suspend fun updateVacancy(vacancy: VacancyEntity)

    @Query("SELECT COUNT(*) FROM vacancies WHERE isFavorite = 1")
    fun getFavoriteVacanciesCount(): LiveData<Int>
}