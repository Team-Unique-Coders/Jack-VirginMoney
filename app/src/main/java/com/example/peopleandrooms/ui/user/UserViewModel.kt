package com.example.peopleandrooms.ui.user

import androidx.lifecycle.ViewModel
import com.example.peopleandrooms.data.peopledata.PeopleDataItemModel
import com.example.peopleandrooms.data.repo.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    suspend fun getUser(userId: String): List<PeopleDataItemModel> {
        return repository.getUser(userId)
    }
}
