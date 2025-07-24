package com.example.peopleandrooms.data.api

import com.example.peopleandrooms.data.peopledata.PeopleData
import com.example.peopleandrooms.data.peopledata.PeopleDataItemModel
import com.example.peopleandrooms.data.roomdata.RoomDataItemModel
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    //Get functions and what not
    //get all
    //get employee detail

    @GET(value = ApiDetails.rooms)
    suspend fun getAllRooms(): List<RoomDataItemModel>;

    @GET(value = ApiDetails.people)
    suspend fun getAllPeople(): PeopleData;

    @GET(ApiDetails.people)
    suspend fun getPersonById(@Query("firstName") id: String): List<PeopleDataItemModel>

}