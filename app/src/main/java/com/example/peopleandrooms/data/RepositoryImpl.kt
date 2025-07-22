package com.example.peopleandrooms.data

class RepositoryImpl(val apiService: ApiService): Repository {
    override suspend fun getAllRooms(): List<RoomDataItemModel> {
        return apiService.getAllRooms()
    }

    override suspend fun getAllPeople(): List<PeopleDataItemModel> {
        return apiService.getAllPeople()
    }


}