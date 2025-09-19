package com.example.anemoneapp.data.model

import com.google.gson.annotations.SerializedName

data class LoginResponse(
	@SerializedName("access_token")
	val accessToken: String,
	val user: User
)

data class User(
	val role: String,
	@SerializedName("updated_at")
	val updatedAt: String,
	val name: String,
	@SerializedName("created_at")
	val createdAt: String,
	@SerializedName("email_verified_at")
	val emailVerifiedAt: Any?,
	val id: Int,
	val email: String,
	val username: String
)

