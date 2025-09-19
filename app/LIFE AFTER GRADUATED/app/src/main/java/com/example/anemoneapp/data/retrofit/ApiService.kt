package com.example.anemoneapp.data.retrofit

import com.example.anemoneapp.data.model.LoginRequest
import com.example.anemoneapp.data.model.LoginResponse
import com.example.anemoneapp.data.model.Student
import com.example.anemoneapp.data.model.StudentRequest
import com.example.anemoneapp.data.model.StudentResponse
import com.example.anemoneapp.data.model.StudentUpdateRequest
import com.example.anemoneapp.data.model.Teacher
import com.example.anemoneapp.data.model.TeacherRequest
import com.example.anemoneapp.data.model.TeacherResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {
    @POST("api/login")
    suspend fun login(@Body loginRequest: LoginRequest): Response<LoginResponse>

    @GET("api/students")
    suspend fun getStudents(@Header("Authorization") token: String): Response<List<Student>>

    @POST("api/students")
    suspend fun addStudent(
        @Header("Authorization") token: String,
        @Body student: StudentRequest
    ): Response<StudentResponse>

    @PUT("api/students/{id}")
    suspend fun updateStudent(
        @Path("id") id: Int,
        @Header("Authorization") token: String,
        @Body student: StudentUpdateRequest
    ): Response<Student>


    @DELETE("api/students/{id}")
    suspend fun deleteStudent(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): Response<Void>

    @DELETE("api/students/{id}")
    suspend fun deleteStudent(
        @Path("id") id: Int,
        @Header("Authorization") token: String
    ): Response<Unit>

    @GET("api/students/{id}")
    suspend fun getStudentById(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): Response<Student>

    @GET("api/teachers")
    suspend fun getTeachers(@Header("Authorization") token: String): Response<List<Teacher>>

    @POST("api/teachers")
    suspend fun addTeacher(
        @Header("Authorization") token: String,
        @Body request: TeacherRequest
    ): Response<TeacherResponse>

    // ApiService.kt
    @PUT("api/teachers/{id}")
    suspend fun updateTeacher(
        @Header("Authorization") token: String,
        @Path("id") id: Int,
        @Body request: TeacherRequest
    ): Response<TeacherResponse>

    @DELETE("api/teachers/{id}")
    suspend fun deleteTeacher(
        @Path("id") teacherId: Int,
        @Header("Authorization") token: String
    ): Response<Unit>


    @GET("api/teachers/{id}")
    suspend fun getTeacherById(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): Response<Teacher>











}