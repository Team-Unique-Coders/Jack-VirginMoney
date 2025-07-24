package com.example.peopleandrooms.data.repo

import com.example.peopleandrooms.data.peopledata.PeopleData
import com.example.peopleandrooms.data.peopledata.PeopleDataItemModel
import com.example.peopleandrooms.data.roomdata.RoomDataItemModel

interface Repository {
    suspend fun getAllRooms(): List<RoomDataItemModel>

    suspend fun getAllPeople(): PeopleData

    suspend fun getUser(firstName: String): List<PeopleDataItemModel>

}