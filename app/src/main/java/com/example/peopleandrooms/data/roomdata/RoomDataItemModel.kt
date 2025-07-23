package com.example.peopleandrooms.data.roomdata


import com.google.gson.annotations.SerializedName

data class RoomDataItemModel(
    @SerializedName("createdAt")
    val createdAt: String = "",
    @SerializedName("id")
    val id: String = "",
    @SerializedName("isOccupied")
    val isOccupied: Boolean = false,
    @SerializedName("maxOccupancy")
    val maxOccupancy: Int = 0
)