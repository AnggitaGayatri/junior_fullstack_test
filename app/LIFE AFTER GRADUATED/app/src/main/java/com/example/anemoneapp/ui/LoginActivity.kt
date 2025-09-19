package com.example.anemoneapp.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.anemoneapp.MainActivity
import com.example.anemoneapp.data.repository.AuthRepository
import com.example.anemoneapp.databinding.ActivityLoginBinding
import com.example.anemoneapp.ui.viewModel.LoginViewModel
import com.example.anemoneapp.utils.PreferenceManager

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val repository = AuthRepository()
        viewModel = ViewModelProvider(this, LoginViewModelFactory(repository))
            .get(LoginViewModel::class.java)

        binding.btnLogin.setOnClickListener {
            val username = binding.loginUsernameInputEditText.text.toString()
            val password = binding.loginPasswordInputEditText.text.toString()

            if (username.isNotBlank() && password.isNotBlank()) {
                viewModel.login(username, password)
            } else {
                Toast.makeText(this, "Username dan Password wajib diisi", Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.loginResult.observe(this) { response ->
            if (response != null && response.isSuccessful) {
                val token = response.body()?.accessToken
                Toast.makeText(this, "Login sukses: $token", Toast.LENGTH_SHORT).show()
                if (token != null) {
                    val prefManager = PreferenceManager(this)

                    prefManager.saveToken(token)
                    prefManager.saveUsername(response.body()?.user?.name ?: "User")
                    prefManager.saveRole(response.body()?.user?.role ?: "user")


                    // Tambahkan log untuk cek
                    Log.d("PrefCheck", "Token: ${prefManager.getToken()}")
                    Log.d("PrefCheck", "Username: ${prefManager.getUsername()}")
                    Log.d("PrefCheck", "Role: ${prefManager.getRole()}")
                }

                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish() // supaya tidak kembali ke login jika tekan back
            } else {
                Toast.makeText(this, "Login gagal", Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.isLoading.observe(this) { loading ->
            binding.progressBar.visibility = if (loading) android.view.View.VISIBLE else android.view.View.GONE
        }

    }
}