package com.example.mandatoryurban.Repo;

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.mandatoryurban.Model.Person
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class FriendRepo {
    private val baseUrl = "https://birthdaysrest.azurewebsites.net"

    private val appService: AppService
    val persons: MutableState<List<Person>> = mutableStateOf(listOf())
    val isLoadingPersons = mutableStateOf(false)
    val errorMessage = mutableStateOf("")

    init {
        val build: Retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create()) // GSON
            .build()
        appService = build.create(AppService::class.java)
        getPersons()
    }

    fun getPersons() {
        isLoadingPersons.value = true
        appService.getAllPersons().enqueue(object : Callback<List<Person>> {
            override fun onResponse(call: Call<List<Person>>, response: Response<List<Person>>) {
                isLoadingPersons.value = false
                if (response.isSuccessful) {
                    val personList: List<Person>? = response.body()
                    persons.value = personList ?: emptyList()
                } else {
                    val message = response.code().toString() + " " + response.message()
                }
            }

            override fun onFailure(call: Call<List<Person>>, t: Throwable) {
                isLoadingPersons.value = false
                val message = t.message ?: "No connection to back-end"
                errorMessage.value = message
            }
        })
    }

    fun getPersonById(personId: Int) {
        isLoadingPersons.value = true
        appService.getPersonById(personId).enqueue(object : Callback<Person> {
            override fun onResponse(call: Call<Person>, response: Response<Person>) {
                if (response.isSuccessful) {
                    getPersons()
                } else {
                    val message = response.code().toString() + " " + response.message()

                }
            }

            override fun onFailure(call: Call<Person>, t: Throwable) {
                val message = t.message ?: "No connection to back-end"
                errorMessage.value = message

            }
        })
    }

    fun add(person:Person) {
        appService.createPerson(person).enqueue(object : Callback<Person> {
            override fun onResponse(call: Call<Person>, response: Response<Person>) {
                if (response.isSuccessful) {
                    getPersons()
                } else {
                    val message = response.code().toString() + " " + response.message()
                }
            }

            override fun onFailure(call: Call<Person>, t: Throwable) {
                val message = t.message ?: "No connection to back-end"
            }
        })
    }

    fun delete(id: Int) {
        appService.deletePerson(id).enqueue(object : Callback<Person> {
            override fun onResponse(call: Call<Person>, response: Response<Person>) {
                if (response.isSuccessful) {
                    getPersons()
                } else {
                    val message = response.code().toString() + " " + response.message()
                }
            }

            override fun onFailure(call: Call<Person>, t: Throwable) {
                val message = t.message ?: "No connection to back-end"
            }
        })
    }

    fun update(personId: Int, person: Person) {
        appService.updatePerson(personId, person).enqueue(object : Callback<Person> {
            override fun onResponse(call: Call<Person>, response: Response<Person>) {
                if (response.isSuccessful) {
                    getPersons()
                } else {
                    val message = response.code().toString() + " " + response.message()
                }
            }

            override fun onFailure(call: Call<Person>, t: Throwable) {
                val message = t.message ?: "No connection to back-end"
            }
        })
    }

    fun sortFriendsByAge(ascending: Boolean) {
        if (ascending)
            persons.value = persons.value.sortedBy { it.age }
        else
            persons.value = persons.value.sortedByDescending { it.age }
    }

    fun sortFriendsByDate(ascending: Boolean) {
        if (ascending)
            persons.value = persons.value.sortedBy { it.getDateSort() }
        else
            persons.value = persons.value.sortedByDescending { it.getDateSort() }

    }

    fun sortFriendsByName(ascending: Boolean) {
        if (ascending)
            persons.value = persons.value.sortedBy { it.name }
        else
            persons.value = persons.value.sortedByDescending { it.name }
    }
    fun filterByName(nameFragment: String) {
        if (nameFragment.isEmpty()) {
            getPersons()
            return
        }
        persons.value =
            persons.value.filter {
                it.name.contains(nameFragment, ignoreCase = true)
            }
    }
}