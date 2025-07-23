package com.example.peopleandrooms.data.repo

import com.example.peopleandrooms.data.api.ApiService
import com.example.peopleandrooms.data.peopledata.PeopleDataItemModel
import com.example.peopleandrooms.data.roomdata.RoomDataItemModel

class RepositoryImpl(val apiService: ApiService): Repository {
    override suspend fun getAllRooms(): List<RoomDataItemModel> {
        return apiService.getAllRooms()
    }

    override suspend fun getAllPeople(): List<PeopleDataItemModel> {
        return apiService.getAllPeople()
    }


}