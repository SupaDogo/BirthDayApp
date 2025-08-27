package com.example.mandatoryurban.Repo
import retrofit2.Call
import retrofit2.http.*
import com.example.mandatoryurban.Model.Person

interface AppService {
    @GET("/api/Persons")
    fun getAllPersons(): Call<List<Person>>

    @GET("/api/Persons/{id}")
    fun getPersonById(@Path("id") personId: Int): Call<Person>

    @POST("/api/Persons")
    fun createPerson(@Body person: Person): Call<Person>

    @DELETE("/api/Persons/{id}")
    fun deletePerson(@Path("id") id: Int): Call<Person>

    @PUT("/api/Persons/{id}")
    fun updatePerson(@Path("id") id: Int, @Body person: Person): Call<Person>
}
