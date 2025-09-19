package com.example.anemoneapp.ui

import UpdateTeacher
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.anemoneapp.data.repository.AuthRepository
import com.example.anemoneapp.databinding.ActivityTeacherBinding
import com.example.anemoneapp.ui.adapter.TeacherAdapter
import com.example.anemoneapp.ui.viewModel.TeacherViewModel
import com.example.anemoneapp.utils.PreferenceManager

//import kotlin.jvm.java


class TeacherActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTeacherBinding
    private lateinit var viewModel: TeacherViewModel
    private lateinit var adapter: TeacherAdapter
    private lateinit var prefManager: PreferenceManager
    private var token: String = ""

    // Launcher untuk AddTeacherActivity
    private val addTeacherLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            if (token.isNotEmpty()) {
                viewModel.fetchTeachers(token) // refresh list guru dari API
            }
        }
    }

    // Launcher untuk UpdateTeacherActivity
    private val updateTeacherLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            if (token.isNotEmpty()) {
                viewModel.fetchTeachers(token) // refresh list guru setelah update
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTeacherBinding.inflate(layoutInflater)
        setContentView(binding.root)

        prefManager = PreferenceManager(this)
        token = prefManager.getToken() ?: ""

        adapter = TeacherAdapter()

        binding.rvStudents.layoutManager = LinearLayoutManager(this)
        binding.rvStudents.adapter = adapter

        val repository = AuthRepository()
        viewModel = TeacherViewModel.TeacherViewModelFactory(repository)
            .create(TeacherViewModel::class.java)

        // Observers
        viewModel.teachers.observe(this) { teachers ->
            adapter.setTeachers(teachers)
        }

        viewModel.isLoading.observe(this) { loading ->
            binding.progressBar.visibility = if (loading) View.VISIBLE else View.GONE
        }

        viewModel.errorMessage.observe(this) { msg ->
            Toast.makeText(this, "Error: $msg", Toast.LENGTH_SHORT).show()
        }

        // Observe delete response
        viewModel.deleteResponse.observe(this) { response ->
            if (response.isSuccessful) {
                Toast.makeText(this, "Guru berhasil dihapus", Toast.LENGTH_SHORT).show()
                viewModel.fetchTeachers(token) // refresh setelah delete
            } else {
                Toast.makeText(this, "Gagal hapus: ${response.code()}", Toast.LENGTH_SHORT).show()
            }
        }

        // Fetch data guru pertama kali
        if (token.isNotEmpty()) {
            viewModel.fetchTeachers(token)
        }

        // Tombol Add Teacher
        binding.fab.setOnClickListener {
            val intent = Intent(this, AddTeacherActivity::class.java)
            addTeacherLauncher.launch(intent)
        }

        // Tombol Edit dan Delete di adapter
        adapter.onEditClick = { teacher ->
            val intent = Intent(this, UpdateTeacher::class.java)
            intent.putExtra("teacher_id", teacher.id)
            intent.putExtra("nip", teacher.nip)
            intent.putExtra("full_name", teacher.full_name)
            intent.putExtra("subject_id", teacher.subject_id)
            intent.putExtra("phone_number", teacher.phone_number)
            intent.putExtra("status", teacher.status)
            updateTeacherLauncher.launch(intent)
        }

        adapter.onDeleteClick = { teacher ->
            showDeleteDialog(teacher.id)
        }
    }

    private fun showDeleteDialog(teacherId: Int) {
        val dialogView = layoutInflater.inflate(com.example.anemoneapp.R.layout.delete_dialog, null)
        val alertDialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .setCancelable(false)
            .create()

        val btnYes = dialogView.findViewById<Button>(com.example.anemoneapp.R.id.btnYes)
        val btnNo = dialogView.findViewById<Button>(com.example.anemoneapp.R.id.btnNo)

        btnYes.setOnClickListener {
            viewModel.deleteTeacher(token, teacherId)
            alertDialog.dismiss()
        }

        btnNo.setOnClickListener {
            alertDialog.dismiss()
        }

        alertDialog.show()
    }
}
