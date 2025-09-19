package com.example.anemoneapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.anemoneapp.data.model.Student
import com.example.anemoneapp.databinding.ItemRowSiswaBinding

class StudentAdapter(private val role: String) :
    RecyclerView.Adapter<StudentAdapter.StudentViewHolder>() {

    private var students: List<Student> = emptyList()

    var onEditClick: ((Student) -> Unit)? = null
    var onDeleteClick: ((Student) -> Unit)? = null

    fun setStudents(newStudents: List<Student>) {
        students = newStudents
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val binding =
            ItemRowSiswaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StudentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        val student = students[position]
        holder.bind(student)
    }

    override fun getItemCount(): Int = students.size

    inner class StudentViewHolder(private val binding: ItemRowSiswaBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(student: Student) {
            binding.tvNama.text = student.name
            binding.tvNis.text = student.student_number

            // Tombol hanya tampil jika role admin
            val isAdmin = role == "admin"
            binding.btnEdit.visibility = if (isAdmin) View.VISIBLE else View.GONE
            binding.btnDelete.visibility = if (isAdmin) View.VISIBLE else View.GONE

            // Klik tombol
            binding.btnEdit.setOnClickListener { onEditClick?.invoke(student) }
            binding.btnDelete.setOnClickListener { onDeleteClick?.invoke(student) }
        }
    }
}
