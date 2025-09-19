package com.example.anemoneapp.ui

import EditSiswaViewModel
import EditSiswaViewModel.EditSiswaViewModelFactory
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.anemoneapp.data.model.StudentUpdateRequest
import com.example.anemoneapp.data.repository.AuthRepository
import com.example.anemoneapp.databinding.ActivityEditSiswaBinding
import com.example.anemoneapp.utils.PreferenceManager

@Suppress("DEPRECATION")
class EditSiswaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditSiswaBinding
    private lateinit var viewModel: EditSiswaViewModel
    private lateinit var prefManager: PreferenceManager
    private var studentId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditSiswaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        prefManager = PreferenceManager(this)
        val token = prefManager.getToken() ?: ""

        // Ambil studentId dari intent
        studentId = intent.getIntExtra("student_id", 0)
        if (studentId == 0) {
            Toast.makeText(this, "Invalid student ID", Toast.LENGTH_SHORT).show()
            finish()
        }

        val repository = AuthRepository()
        viewModel = ViewModelProvider(
            this,
            EditSiswaViewModelFactory(repository)
        )[EditSiswaViewModel::class.java]

        // ✅ Observe detail student dari API
        viewModel.student.observe(this) { student ->
            student?.let {
                binding.edNIS.setText(it.student_number)
                binding.edNama.setText(it.name)
                binding.edKelas.setText(it.`class`)
                binding.edAlamat.setText(it.address)
            }
        }

        // ✅ Observe update response
        viewModel.updateResponse.observe(this) { response ->
            if (response.isSuccessful) {
                Toast.makeText(this, "Student updated successfully!", Toast.LENGTH_SHORT).show()
                setResult(RESULT_OK)
                finish()
            } else {
                Toast.makeText(this, "Failed: ${response.code()}", Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.errorMessage.observe(this) { msg ->
            Toast.makeText(this, "Error: $msg", Toast.LENGTH_SHORT).show()
        }

        // ✅ Fetch student detail dari API saat activity dibuka
        if (token.isNotEmpty()) {
            viewModel.fetchStudentById(token, studentId)
        }

        // Save button
        binding.btnSave.setOnClickListener {
            val updatedStudent = StudentUpdateRequest(
                student_number = binding.edNIS.text.toString().trim(),
                name = binding.edNama.text.toString().trim(),
                `class` = binding.edKelas.text.toString().trim(),
                address = binding.edAlamat.text.toString().trim()
            )
            if (token.isNotEmpty()) {
                viewModel.updateStudent(token, studentId, updatedStudent)
            } else {
                Toast.makeText(this, "Token not found. Please login again.", Toast.LENGTH_SHORT).show()
            }
        }

        // Back button
        binding.fabBack.setOnClickListener { finish() }
    }
}
