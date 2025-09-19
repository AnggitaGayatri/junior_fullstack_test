
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

class UpdateTeacherViewModel(private val repository: AuthRepository) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _updateResponse = MutableLiveData<Response<TeacherResponse>>()
    val updateResponse: LiveData<Response<TeacherResponse>> get() = _updateResponse

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    fun updateTeacher(token: String, id: Int, request: TeacherRequest) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val response = repository.updateTeacher(token, id, request)
                _isLoading.value = false
                _updateResponse.postValue(response)
            } catch (e: Exception) {
                _isLoading.value = false
                _errorMessage.postValue(e.message ?: "Terjadi kesalahan")
            }
        }
    }

    class Factory(private val repository: AuthRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(UpdateTeacherViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return UpdateTeacherViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
