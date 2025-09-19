import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.anemoneapp.data.model.TeacherRequest
import com.example.anemoneapp.data.repository.AuthRepository
import com.example.anemoneapp.databinding.ActivityAddTeacherBinding
import com.example.anemoneapp.utils.PreferenceManager

class UpdateTeacherActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddTeacherBinding // bisa reuse layout AddTeacher
    private lateinit var viewModel: UpdateTeacherViewModel
    private lateinit var prefManager: PreferenceManager

    private var teacherId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddTeacherBinding.inflate(layoutInflater)
        setContentView(binding.root)

        prefManager = PreferenceManager(this)
        val token = prefManager.getToken() ?: ""


        // ambil data dari intent
        teacherId = intent.getIntExtra("teacher_id", -1)
        binding.edNIP.setText(intent.getStringExtra("nip"))
        binding.edNama.setText(intent.getStringExtra("full_name"))
        binding.edmapel.setText(intent.getIntExtra("subject_id", 0).toString())
        binding.edtelp.setText(intent.getStringExtra("phone_number"))
        binding.edStatus.setText(intent.getStringExtra("status"))

        val repository = AuthRepository()
        viewModel = ViewModelProvider(this, UpdateTeacherViewModel.Factory(repository))[UpdateTeacherViewModel::class.java]

        // observer response
        viewModel.updateResponse.observe(this) { response ->
            if (response.isSuccessful) {
                Toast.makeText(this, "Guru berhasil diperbarui!", Toast.LENGTH_SHORT).show()
                setResult(RESULT_OK)
                finish()
            } else {
                Toast.makeText(this, "Update gagal: ${response.code()}", Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.errorMessage.observe(this) {
            Toast.makeText(this, "Error: $it", Toast.LENGTH_SHORT).show()
        }

        // tombol save
        binding.btnSave.setOnClickListener {
            val request = TeacherRequest(
                nip = binding.edNIP.text.toString(),
                full_name = binding.edNama.text.toString(),
                subject_id = binding.edmapel.text.toString().toInt(),
                phone_number = binding.edtelp.text.toString(),
                status = binding.edStatus.text.toString()
            )
            if (token.isNotEmpty() && teacherId != -1) {
                viewModel.updateTeacher(token, teacherId, request)
            }
        }

        // tombol back
        binding.fabBack.setOnClickListener { finish() }
    }
}
