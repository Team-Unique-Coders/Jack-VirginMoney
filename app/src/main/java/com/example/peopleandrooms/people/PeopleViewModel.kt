package com.example.peopleandrooms.people

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.peopleandrooms.data.PeopleDataItemModel
import com.example.peopleandrooms.data.Repository
import com.example.peopleandrooms.data.RoomDataItemModel
import com.example.peopleandrooms.rooms.RoomsApiResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class PeopleViewModel  @Inject constructor(val repository: Repository): ViewModel() {
    private val _personData = MutableLiveData<PersonApiResponse>()
    val personData: LiveData<PersonApiResponse> = _personData

    init {
        getPersons()

    }

    fun getPersons() {
        _personData.postValue(
            PersonApiResponse.Loading
        )
        viewModelScope.launch {
            try {

                val personDetail = repository.getAllPeople()
                _personData.postValue(PersonApiResponse.Success(personDetail))

            } catch (e: Exception) {
                _personData.postValue(PersonApiResponse.Error("no response"))
            }
        }
    }
}


    sealed class PersonApiResponse {
        object Loading : PersonApiResponse()
        data class Success(val personSuccess: List<PeopleDataItemModel>) : PersonApiResponse()
        data class Error(val personError: String) : PersonApiResponse()
    }

