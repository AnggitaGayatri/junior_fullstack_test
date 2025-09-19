import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.anemoneapp.data.model.Student
import com.example.anemoneapp.data.model.StudentUpdateRequest
import com.example.anemoneapp.data.repository.AuthRepository
import kotlinx.coroutines.launch
import retrofit2.Response

class EditSiswaViewModel(private val repository: AuthRepository) : ViewModel() {

    private val _student = MutableLiveData<Student?>()
    val student: LiveData<Student?> = _student

    private val _updateResponse = MutableLiveData<Response<Student>>()
    val updateResponse: LiveData<Response<Student>> = _updateResponse

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    fun fetchStudentById(token: String, id: Int) {
        viewModelScope.launch {
            try {
                val response = repository.getStudentById(token, id)
                if (response.isSuccessful) {
                    _student.postValue(response.body())
                } else {
                    _errorMessage.postValue("Failed: ${response.code()}")
                }
            } catch (e: Exception) {
                _errorMessage.postValue("Exception: ${e.message}")
            }
        }
    }

    fun updateStudent(token: String, id: Int, request: StudentUpdateRequest) {
        viewModelScope.launch {
            try {
                val response = repository.updateStudent(token, id, request)
                _updateResponse.postValue(response)
            } catch (e: Exception) {
                _errorMessage.postValue("Exception: ${e.message}")
            }
        }
    }

    class EditSiswaViewModelFactory(
        private val repository: AuthRepository
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(EditSiswaViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return EditSiswaViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
