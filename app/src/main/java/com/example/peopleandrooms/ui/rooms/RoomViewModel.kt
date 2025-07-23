package com.example.peopleandrooms.ui.rooms

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.peopleandrooms.data.repo.Repository
import com.example.peopleandrooms.data.roomdata.RoomDataItemModel
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class RoomViewModel @Inject constructor(val repository: Repository): ViewModel() {
    private val _roomData = MutableLiveData<RoomsApiResponse>()
    val roomData : LiveData<RoomsApiResponse> = _roomData

    init { getRooms() }

    fun getRooms(){
        _roomData.postValue(
            RoomsApiResponse.Loading
        )
        viewModelScope.launch {
            try{

                val roomsDetail = repository.getAllRooms()
                _roomData.postValue(RoomsApiResponse.Success(roomsDetail))
            } catch (e:Exception){
                _roomData.postValue(RoomsApiResponse.Error("no response"))
            } }
    }
}

sealed class RoomsApiResponse {
    object Loading : RoomsApiResponse()
    data class Success(val roomsModel: List<RoomDataItemModel>) : RoomsApiResponse()
    data class Error(val roomError: String) : RoomsApiResponse()
}
