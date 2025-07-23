package com.example.peopleandrooms.data.api

import com.example.peopleandrooms.data.peopledata.PeopleDataItemModel
import com.example.peopleandrooms.data.roomdata.RoomDataItemModel
import retrofit2.http.GET

interface ApiService {
    //Get functions and what not
    //get all
    @GET(value = ApiDetails.rooms)
    suspend fun getAllRooms(): List<RoomDataItemModel>;

    @GET(value = ApiDetails.people)
    suspend fun getAllPeople(): List<PeopleDataItemModel>;
}