package com.example.mandatoryurban.Model

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import com.example.mandatoryurban.Repo.FriendRepo

class PersonViewModel:ViewModel() {
    private val repository = FriendRepo()
    val persons: State<List<Person>> = repository.persons
    val errorMessage: State<String> = repository.errorMessage
    val isLoadingPersons: State<Boolean> = repository.isLoadingPersons
    private var lastId: Int = 0

    fun getLastId(): Int{
        getNextId()
        return lastId
    }
    fun getPersons() {
        repository.getPersons()
    }

    fun addPerson(person: Person) {
        repository.add(person)
    }

    private fun getNextId() {
    var person = persons.value.get(persons.value.size - 1)
        var personId = person.id+1
        lastId = personId

    }

    fun deletePerson(personId: Int) {
        repository.delete(personId)
    }

    fun updatePerson(personId: Int, person: Person) {
        repository.update(personId, person)
    }

    fun getPersonById(personId: Int){
        repository.getPersonById(personId)
    }

    fun sortFriendsByAge(ascending: Boolean){
        repository.sortFriendsByAge(ascending)
    }
    fun sortFriendsByName(ascending: Boolean){
        repository.sortFriendsByName(ascending)
    }
    fun sortFriendsByDate(ascending: Boolean){
        repository.sortFriendsByDate(ascending)
    }

    fun filterByName(nameFragment: String){
        repository.filterByName(nameFragment)
    }


}