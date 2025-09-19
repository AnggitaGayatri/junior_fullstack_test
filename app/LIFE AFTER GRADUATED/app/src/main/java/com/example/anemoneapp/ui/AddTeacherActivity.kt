package com.example.anemoneapp.ui

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.anemoneapp.data.model.TeacherRequest
import com.example.anemoneapp.data.repository.AuthRepository
import com.example.anemoneapp.databinding.ActivityAddTeacherBinding
import com.example.anemoneapp.ui.viewModel.AddTeacherViewModel
import com.example.anemoneapp.ui.viewModel.AddTeacherViewModel.Factory
import com.example.anemoneapp.utils.PreferenceManager

class AddTeacherActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddTeacherBinding
    private lateinit var viewModel: AddTeacherViewModel
    private lateinit var prefManager: PreferenceManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddTeacherBinding.inflate(layoutInflater)
        setContentView(binding.root)

        prefManager = PreferenceManager(this)
        val token = prefManager.getToken() ?: ""

        // Repository & ViewModel
        val repository = AuthRepository()
        viewModel = ViewModelProvider(
            this,
            Factory(repository)
        )[AddTeacherViewModel::class.java]

        // Isi dropdown Status
        val statusOptions = listOf("aktif", "nonaktif")
        val adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, statusOptions)
        binding.edStatus.setAdapter(adapter)

        binding.edStatus.setOnClickListener {
            binding.edStatus.showDropDown()
        }


        // Observasi response API
        viewModel.addTeacherResponse.observe(this) { response ->
            if (response.isSuccessful) {
                Toast.makeText(this, "Guru berhasil ditambahkan!", Toast.LENGTH_SHORT).show()
                setResult(RESULT_OK)
                finish() // balik ke TeacherActivity
            } else {
                Toast.makeText(this, "Gagal: ${response.code()}", Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.errorMessage.observe(this) { msg ->
            Toast.makeText(this, "Error: $msg", Toast.LENGTH_SHORT).show()
        }



        // Tombol Save
        binding.btnSave.setOnClickListener {
            val nip = binding.edNIP.text.toString().trim()
            val nama = binding.edNama.text.toString().trim()
            val mapel = binding.edmapel.text.toString().trim()
            val telp = binding.edtelp.text.toString().trim()
            val status = binding.edStatus.text.toString().trim()

            if (nip.isEmpty() || nama.isEmpty() || mapel.isEmpty() || telp.isEmpty() || status.isEmpty()) {
                Toast.makeText(this, "Semua field harus diisi", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val request = TeacherRequest(
                nip = nip,
                full_name = nama,
                subject_id = mapel.toInt(),
                phone_number = telp,
                status = status
            )

            if (token.isNotEmpty()) {
                viewModel.addTeacher(token, request)
            } else {
                Toast.makeText(this, "Token tidak ditemukan, silakan login lagi", Toast.LENGTH_SHORT).show()
            }
        }

        // Tombol Back
        binding.fabBack.setOnClickListener { finish() }
    }
}
