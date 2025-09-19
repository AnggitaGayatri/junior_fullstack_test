package com.example.anemoneapp.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.anemoneapp.data.model.StudentRequest
import com.example.anemoneapp.data.repository.AuthRepository
import com.example.anemoneapp.databinding.ActivityTambahSiswaBinding
import com.example.anemoneapp.ui.viewModel.TambahSiswaViewModel
import com.example.anemoneapp.ui.viewModel.TambahSiswaViewModelFactory
import com.example.anemoneapp.utils.PreferenceManager

class TambahSiswa : AppCompatActivity() {

    private lateinit var binding: ActivityTambahSiswaBinding
    private lateinit var viewModel: TambahSiswaViewModel
    private lateinit var prefManager: PreferenceManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTambahSiswaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        prefManager = PreferenceManager(this)
        val token = prefManager.getToken() ?: ""

        // ViewModel setup
        val repository = AuthRepository()
        viewModel = ViewModelProvider(
            this,
            TambahSiswaViewModelFactory(repository)
        )[TambahSiswaViewModel::class.java]

        // Observe
        viewModel.isLoading.observe(this) { loading ->
//            //
        }

        viewModel.addStudentResponse.observe(this) { response ->
            if (response.isSuccessful) {
                Toast.makeText(this, "Student added successfully!", Toast.LENGTH_SHORT).show()
                setResult(RESULT_OK)
                finish()
                finish() // Kembali ke daftar siswa
            } else {
                Toast.makeText(this, "Failed: ${response.code()}", Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.errorMessage.observe(this) { msg ->
            Toast.makeText(this, "Error: $msg", Toast.LENGTH_SHORT).show()
        }

        // Button click
        binding.btnSave.setOnClickListener {
            val student = StudentRequest(
                student_number = binding.edNIS.text.toString().trim(),
                name = binding.edNama.text.toString().trim(),
                `class` = binding.edKelas.text.toString().trim(),
                address = binding.edAlamat.text.toString().trim()
            )

            if (token.isNotEmpty()) {
                viewModel.addStudent(token, student)
            } else {
                Toast.makeText(this, "Token not found. Please login again.", Toast.LENGTH_SHORT).show()
            }
        }

        // FAB back
        binding.fabBack.setOnClickListener { finish() }
    }
}
