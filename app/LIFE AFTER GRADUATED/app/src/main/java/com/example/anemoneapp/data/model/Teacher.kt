package com.example.anemoneapp.data.model

data class Teacher(
    val id: Int,
    val nip: String,
    val full_name: String,
    val subject_id: Int,
    val phone_number: String,
    val status: String,
    val created_at: String,
    val updated_at: String
)

data class TeacherRequest(
    val nip: String,
    val full_name: String,
    val subject_id: Int,
    val phone_number: String,
    val status: String
)

data class TeacherResponse(
    val id: Int,
    val nip: String,
    val full_name: String,
    val subject_id: Int,
    val phone_number: String,
    val status: String,
    val created_at: String,
    val updated_at: String
)


