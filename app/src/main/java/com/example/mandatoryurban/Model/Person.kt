package com.example.mandatoryurban.Model

import java.time.LocalDate
import java.time.Month

data class Person(val id: Int,
                  val userId: String?,
                  val name: String,
                  val birthYear: Int,
                  val birthMonth: Int,
                  val birthDayOfMonth: Int,
                  val remarks : String?,
                  val pictureUrl:String?,
                  val age: Int) {

    fun getDateSort():LocalDate{
        val now = LocalDate.now()
        return LocalDate.of(now.year,birthMonth,birthDayOfMonth)
    }
}