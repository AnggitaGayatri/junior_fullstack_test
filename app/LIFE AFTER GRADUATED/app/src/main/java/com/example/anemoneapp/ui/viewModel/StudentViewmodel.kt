package com.example.anemoneapp.ui.viewModel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.anemoneapp.data.model.Student
import com.example.anemoneapp.data.repository.AuthRepository
import kotlinx.coroutines.launch
import retrofit2.Response

class StudentViewModel(private val repository: AuthRepository) : ViewModel() {

    private val _students = MutableLiveData<List<Student>>()
    val students: LiveData<List<Student>> = _students

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage
    private val _deleteResponse = MutableLiveData<Response<Unit>>()
    val deleteResponse: LiveData<Response<Unit>> get() = _deleteResponse

    fun deleteStudent(token: String, studentId: Int) {
        viewModelScope.launch {
            try {
                val response = repository.deleteStudent(token, studentId)
                _deleteResponse.value = response
            } catch (e: Exception) {
                // handle error
            }
        }
    }



    fun fetchStudents(token: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response: Response<List<Student>> = repository.getStudents(token)
                if (response.isSuccessful) {
                    _students.value = response.body()
                } else {
                    _errorMessage.value = "Error: ${response.code()}"
                }
            } catch (e: Exception) {
                _errorMessage.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun refreshStudents(token: String) {
        fetchStudents(token)
    }

}

class StudentViewModelFactory(private val repository: AuthRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(StudentViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return StudentViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
