package com.example.anemoneapp.data.repository

import com.example.anemoneapp.data.model.LoginRequest
import com.example.anemoneapp.data.model.LoginResponse
import com.example.anemoneapp.data.model.Student
import com.example.anemoneapp.data.model.StudentRequest
import com.example.anemoneapp.data.model.StudentResponse
import com.example.anemoneapp.data.model.StudentUpdateRequest
import com.example.anemoneapp.data.model.Teacher
import com.example.anemoneapp.data.model.TeacherRequest
import com.example.anemoneapp.data.model.TeacherResponse
import com.example.anemoneapp.data.retrofit.ApiConfig
import retrofit2.Response

class AuthRepository {
    private val apiService = ApiConfig.getApiService()

    suspend fun login(username: String, password: String): Response<LoginResponse> {
        val request = LoginRequest(username, password)
        return apiService.login(request)
    }

    suspend fun getStudents(token: String): Response<List<Student>> {
        val bearer = "Bearer $token"
        return apiService.getStudents(bearer)
    }

    suspend fun addStudent(token: String, student: StudentRequest): Response<StudentResponse> {
        val bearer = "Bearer $token"
        return apiService.addStudent(bearer, student)
    }

    suspend fun updateStudent(
        token: String,
        studentId: Int,
        student: StudentUpdateRequest
    ): Response<Student> {
        val bearer = "Bearer $token"
        return apiService.updateStudent(studentId, bearer, student)
    }

    suspend fun deleteStudent(token: String, studentId: Int): Response<Unit> {
        val bearer = "Bearer $token"
        return apiService.deleteStudent(studentId, bearer)
    }

    suspend fun getStudentById(token: String, id: Int): Response<Student> {
        val bearer = "Bearer $token"
        return apiService.getStudentById(bearer, id)
    }

    suspend fun getTeachers(token: String): Response<List<Teacher>> {
        val bearer = "Bearer $token"
        return apiService.getTeachers(bearer)
    }

    suspend fun addTeacher(token: String, request: TeacherRequest): Response<TeacherResponse> {
        val bearer = "Bearer $token"
        return apiService.addTeacher(bearer, request)
    }

    suspend fun updateTeacher(token: String, id: Int, request: TeacherRequest): Response<TeacherResponse> {
        return apiService.updateTeacher("Bearer $token", id, request)
    }

    suspend fun deleteTeacher(token: String, teacherId: Int): Response<Unit> {
        val bearer = "Bearer $token"
        return apiService.deleteTeacher(teacherId, bearer)
    }
    suspend fun getTeacherById(token: String, id: Int): Response<Teacher> {
        return apiService.getTeacherById("Bearer $token", id)
    }







}