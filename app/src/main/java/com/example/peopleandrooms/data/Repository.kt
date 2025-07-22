package com.example.peopleandrooms.data

interface Repository {
    suspend fun getAllRooms(): List<RoomDataItemModel>

    suspend fun getAllPeople(): List<PeopleDataItemModel>

}