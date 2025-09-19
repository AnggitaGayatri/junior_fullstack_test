package com.example.anemoneapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.anemoneapp.R
import com.example.anemoneapp.data.model.Teacher

class TeacherAdapter : RecyclerView.Adapter<TeacherAdapter.TeacherViewHolder>() {

    private val teacherList = mutableListOf<Teacher>()
    var onEditClick: ((Teacher) -> Unit)? = null
    var onDeleteClick: ((Teacher) -> Unit)? = null

    fun setTeachers(teachers: List<Teacher>) {
        teacherList.clear()
        teacherList.addAll(teachers)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeacherViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_row_siswa, parent, false) // pakai layout siswa
        return TeacherViewHolder(view)
    }

    override fun onBindViewHolder(holder: TeacherViewHolder, position: Int) {
        val teacher = teacherList[position]
        holder.bind(teacher)
    }

    override fun getItemCount(): Int = teacherList.size

    inner class TeacherViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvNama: TextView = itemView.findViewById(R.id.tv_nama)
        private val tvNis: TextView = itemView.findViewById(R.id.tv_nis)
        private val btnEdit: Button = itemView.findViewById(R.id.btn_edit)
        private val btnDelete: Button = itemView.findViewById(R.id.btn_delete)

        fun bind(teacher: Teacher) {
            tvNama.text = teacher.full_name
            tvNis.text = teacher.nip

            btnEdit.setOnClickListener { onEditClick?.invoke(teacher) }
            btnDelete.setOnClickListener { onDeleteClick?.invoke(teacher) }
        }
    }
}
