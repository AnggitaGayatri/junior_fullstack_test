package com.example.anemoneapp.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.anemoneapp.data.model.TeacherRequest
import com.example.anemoneapp.data.model.TeacherResponse
import com.example.anemoneapp.data.repository.AuthRepository
import kotlinx.coroutines.launch
import retrofit2.Response

class AddTeacherViewModel(private val repository: AuthRepository) : ViewModel() {

    // Loading state
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    // Response dari API
    private val _addTeacherResponse = MutableLiveData<Response<TeacherResponse>>()
    val addTeacherResponse: LiveData<Response<TeacherResponse>> get() = _addTeacherResponse

    // Error message
    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    // Fungsi untuk nambah guru
    fun addTeacher(token: String, request: TeacherRequest) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val response = repository.addTeacher(token, request) // suspend fun di Repository
                _addTeacherResponse.postValue(response)
            } catch (e: Exception) {
                _errorMessage.postValue(e.message ?: "Terjadi kesalahan")
            } finally {
                _isLoading.postValue(false)
            }
        }
    }

    // Factory untuk inject repository
    class Factory(private val repository: AuthRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(AddTeacherViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return AddTeacherViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
