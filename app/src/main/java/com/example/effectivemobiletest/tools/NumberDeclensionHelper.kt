package com.example.effectivemobiletest.tools

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

class NumberDeclensionHelper {
    fun getPersonCountText(lookingNumber: Int?): String {
        val num = lookingNumber ?: 0
        val word = when {
            num % 10 in 2..4 -> "человека"
            else -> "человек"
        }
        return "Сейчас просматривает $num $word"
    }

    fun getVacancyCountText(count: Int?): String {
        val num = count ?: 0
        val word = when {
            num % 10 == 1 -> "вакансия"
            num % 10 in 2..4 -> "вакансии"
            else -> "вакансий"
        }
        return "$num $word"
    }

    fun formatPublicationDate(dateString: String): String {
        val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale("ru"))
        val date = LocalDate.parse(dateString, dateFormatter)

        val day = date.dayOfMonth
        val month = getMonth(date.monthValue, day)

        return "Опубликовано $day $month"
    }

    private fun getMonth(month: Int, day: Int): String {
        return when (month) {
            1 -> "января"
            2 -> "февраля"
            3 -> "марта"
            4 -> "апреля"
            5 -> "мая"
            6 -> "июня"
            7 -> "июля"
            8 -> "августа"
            9 -> "сентября"
            10 -> "октября"
            11 -> "ноября"
            else -> "декабря"
        }
    }
}