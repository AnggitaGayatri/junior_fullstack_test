package com.example.anemoneapp.ui.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.anemoneapp.data.model.LoginResponse
import com.example.anemoneapp.data.repository.AuthRepository
import kotlinx.coroutines.launch
import retrofit2.Response

class LoginViewModel(private val repository: AuthRepository) : ViewModel() {

    val loginResult = MutableLiveData<Response<LoginResponse>?>()
    val isLoading = MutableLiveData<Boolean>()

    fun login(username: String, password: String) {
        isLoading.value = true
        viewModelScope.launch {
            try {
                val response = repository.login(username, password)
                loginResult.value = response
            } catch (e: Exception) {
                e.printStackTrace()
                loginResult.value = null
            } finally {
                isLoading.value = false
            }
        }
    }
}