package com.example.peopleandrooms.ui.people

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.peopleandrooms.data.api.ApiDetails
import com.example.peopleandrooms.data.peopledata.PeopleData
import com.example.peopleandrooms.data.peopledata.PeopleDataItemModel
import com.example.peopleandrooms.data.repo.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class PeopleViewModel  @Inject constructor(val repository: Repository): ViewModel() {
    private val _personData = MutableLiveData<PersonApiResponse>()
    val personData: LiveData<PersonApiResponse> = _personData

    init { getPersons() }

    fun getPersons() {
        _personData.postValue(
            PersonApiResponse.Loading
        )
        viewModelScope.launch {
            val attempt: PeopleData = repository.getAllPeople()
            try {
                val personDetail = repository.getAllPeople()
                _personData.postValue(PersonApiResponse.Success(attempt))
            } catch (e: Exception) {
                _personData.postValue(PersonApiResponse.Error("no response"))
            } } }
}

    sealed class PersonApiResponse {
        object Loading : PersonApiResponse()
        data class Success(val personSuccess: PeopleData) : PersonApiResponse()
        data class Error(val personError: String) : PersonApiResponse()
    }

