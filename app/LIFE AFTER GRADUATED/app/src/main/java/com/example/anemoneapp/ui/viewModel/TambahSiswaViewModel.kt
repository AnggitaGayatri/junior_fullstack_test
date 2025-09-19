package com.example.anemoneapp.ui.viewModel


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.anemoneapp.data.model.StudentRequest
import com.example.anemoneapp.data.model.StudentResponse
import com.example.anemoneapp.data.repository.AuthRepository
import kotlinx.coroutines.launch
import retrofit2.Response

class TambahSiswaViewModel(private val repository: AuthRepository) : ViewModel() {

    val isLoading = MutableLiveData<Boolean>()
    val addStudentResponse = MutableLiveData<Response<StudentResponse>>()
    val errorMessage = MutableLiveData<String>()

    fun addStudent(token: String, student: StudentRequest) {
        viewModelScope.launch {
            isLoading.value = true
            try {
                val response = repository.addStudent(token, student)
                addStudentResponse.value = response
            } catch (e: Exception) {
                errorMessage.value = e.message
            } finally {
                isLoading.value = false
            }
        }
    }
}

class TambahSiswaViewModelFactory(private val repository: AuthRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TambahSiswaViewModel::class.java)) {
            return TambahSiswaViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}


