package com.example.anemoneapp.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.anemoneapp.data.model.Teacher
import com.example.anemoneapp.data.repository.AuthRepository
import kotlinx.coroutines.launch
import retrofit2.Response

class TeacherViewModel(private val repository: AuthRepository) : ViewModel() {

    private val _teachers = MutableLiveData<List<Teacher>>()
    val teachers: LiveData<List<Teacher>> = _teachers

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    // 🔹 Tambahin LiveData untuk delete
    private val _deleteResponse = MutableLiveData<Response<Unit>>()
    val deleteResponse: LiveData<Response<Unit>> = _deleteResponse

    fun fetchTeachers(token: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response: Response<List<Teacher>> = repository.getTeachers(token)
                if (response.isSuccessful) {
                    _teachers.postValue(response.body() ?: emptyList())
                } else {
                    _errorMessage.postValue("Error: ${response.code()}")
                }
            } catch (e: Exception) {
                _errorMessage.postValue("Exception: ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun deleteTeacher(token: String, teacherId: Int) {
        viewModelScope.launch {
            try {
                val response = repository.deleteTeacher(token, teacherId)
                _deleteResponse.postValue(response)
            } catch (e: Exception) {
                _errorMessage.postValue("Exception: ${e.message}")
            }
        }
    }

    class TeacherViewModelFactory(private val repository: AuthRepository) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(TeacherViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return TeacherViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
