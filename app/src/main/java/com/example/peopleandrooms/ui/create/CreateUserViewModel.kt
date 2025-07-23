package com.example.peopleandrooms.ui.create

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CreateUserViewModel: ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "Create User Fragment"
    }
    val text: LiveData<String> = _text
}
