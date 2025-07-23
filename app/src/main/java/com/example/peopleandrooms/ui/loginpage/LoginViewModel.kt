package com.example.peopleandrooms.ui.loginpage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LoginViewModel: ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "Login Page Fragment"
    }
    val text: LiveData<String> = _text
}
