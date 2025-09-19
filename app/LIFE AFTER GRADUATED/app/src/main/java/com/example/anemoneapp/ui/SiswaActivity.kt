package com.example.anemoneapp.ui

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.anemoneapp.R
import com.example.anemoneapp.data.repository.AuthRepository
import com.example.anemoneapp.databinding.ActivitySiswaBinding
import com.example.anemoneapp.ui.adapter.StudentAdapter
import com.example.anemoneapp.ui.viewModel.StudentViewModel
import com.example.anemoneapp.ui.viewModel.StudentViewModelFactory
import com.example.anemoneapp.utils.PreferenceManager

class SiswaActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySiswaBinding
    private lateinit var viewModel: StudentViewModel
    private lateinit var adapter: StudentAdapter
    private lateinit var prefManager: PreferenceManager
    private var token: String = ""

    private val tambahSiswaLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            if (token.isNotEmpty()) {
                viewModel.refreshStudents(token)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySiswaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        prefManager = PreferenceManager(this)
        token = prefManager.getToken() ?: ""
        val role = prefManager.getRole() ?: "user"

        // Setup adapter
        adapter = StudentAdapter(role)

        // RecyclerView
        binding.rvStudents.layoutManager = LinearLayoutManager(this)
        binding.rvStudents.adapter = adapter

        // ViewModel
        val repository = AuthRepository()
        viewModel = StudentViewModelFactory(repository).create(StudentViewModel::class.java)

        // Observers
        viewModel.students.observe(this) { students ->
            adapter.setStudents(students)
        }

        viewModel.isLoading.observe(this) { loading ->
            binding.progressBar.visibility = if (loading) View.VISIBLE else View.GONE
        }

        viewModel.errorMessage.observe(this) { msg ->
            Toast.makeText(this, "Error: $msg", Toast.LENGTH_SHORT).show()
        }

        // ✅ observe delete response
        viewModel.deleteResponse.observe(this) { response ->
            if (response.isSuccessful) {
                Toast.makeText(this, "Student deleted!", Toast.LENGTH_SHORT).show()
                viewModel.refreshStudents(token)
            } else {
                Toast.makeText(this, "Delete failed: ${response.code()}", Toast.LENGTH_SHORT).show()
            }
        }

        // Fetch initial data
        if (token.isNotEmpty()) {
            viewModel.fetchStudents(token)
        }

        // FAB only visible for admin
        binding.fab.visibility = if (role == "admin") View.VISIBLE else View.GONE

        binding.fab.setOnClickListener {
            val intent = Intent(this, TambahSiswa::class.java)
            tambahSiswaLauncher.launch(intent)
        }

        // Adapter callbacks for Edit/Delete
        adapter.onEditClick = { student ->
            val intent = Intent(this, EditSiswaActivity::class.java)
            intent.putExtra("student_id", student.id)
            startActivity(intent)
        }

        adapter.onDeleteClick = { student ->
            if (token.isNotEmpty()) {
                showDeleteDialog(student.id)
            }
        }
    }

    // ✅ Show custom delete confirmation dialog
    private fun showDeleteDialog(studentId: Int) {
        val dialogView = layoutInflater.inflate(R.layout.delete_dialog, null)
        val alertDialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .setCancelable(false)
            .create()

        val btnYes = dialogView.findViewById<Button>(R.id.btnYes)
        val btnNo = dialogView.findViewById<Button>(R.id.btnNo)

        btnYes.setOnClickListener {
            viewModel.deleteStudent(token, studentId)
            alertDialog.dismiss()
        }

        btnNo.setOnClickListener {
            alertDialog.dismiss()
        }

        alertDialog.show()
    }
}
