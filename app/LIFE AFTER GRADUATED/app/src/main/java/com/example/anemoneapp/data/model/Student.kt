package com.example.anemoneapp.data.model

data class Student(
    val id: Int,
    val student_number: String,
    val name: String,
    val `class`: String, // backtick karena keyword "class"
    val address: String,
    val created_at: String,
    val updated_at: String
)

data class StudentResponse(
    val message: String,
    val data: Student
)

data class StudentRequest(
    val student_number: String,
    val name: String,
    val `class`: String,
    val address: String
)

data class StudentUpdateRequest(
    val student_number: String,
    val name: String,
    val `class`: String,
    val address: String
)




