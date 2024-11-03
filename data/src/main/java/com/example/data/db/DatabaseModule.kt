package com.example.data.db

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "test_database"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    fun provideVacancyDao(db: AppDatabase): VacancyDao {
        return db.vacancyDao()
    }

    @Provides
    fun provideVacancyRepository(vacancyDao: VacancyDao): VacancyRepository {
        return VacancyRepository(vacancyDao)
    }
}