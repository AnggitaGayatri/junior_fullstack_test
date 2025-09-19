package com.example.anemoneapp.utils

import android.content.Context
import android.content.SharedPreferences

class PreferenceManager(context: Context) {

    private val sharedPref: SharedPreferences =
        context.getSharedPreferences("MyAppPref", Context.MODE_PRIVATE)

    // Token
    fun saveToken(token: String) {
        sharedPref.edit().putString("ACCESS_TOKEN", token).apply()
    }

    fun getToken(): String? {
        return sharedPref.getString("ACCESS_TOKEN", null)
    }

    fun clearToken() {
        sharedPref.edit().remove("ACCESS_TOKEN").apply()
    }

    // Username
    fun saveUsername(username: String) {
        sharedPref.edit().putString("USERNAME", username).apply()
    }

    fun getUsername(): String? {
        return sharedPref.getString("USERNAME", "User")
    }

    // Role
    fun saveRole(role: String) {
        sharedPref.edit().putString("ROLE", role).apply()
    }

    fun getRole(): String? {
        return sharedPref.getString("ROLE", "user")
    }

    // Clear all
    fun clearAll() {
        sharedPref.edit().clear().apply()
    }
}
