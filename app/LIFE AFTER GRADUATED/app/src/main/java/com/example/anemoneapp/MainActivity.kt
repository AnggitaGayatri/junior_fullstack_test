package com.example.anemoneapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.example.anemoneapp.ui.SiswaActivity
import com.example.anemoneapp.ui.TeacherActivity
import com.example.anemoneapp.utils.PreferenceManager

class MainActivity : AppCompatActivity() {

    private lateinit var prefManager: PreferenceManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        prefManager = PreferenceManager(this)

        val username = prefManager.getUsername() ?: "User"
        val role = prefManager.getRole() ?: "user"

        val tvHello = findViewById<TextView>(R.id.tvHelloUser)
        tvHello.text = "Hello, $username"

        // Ambil CardView
        val cardSiswa = findViewById<CardView>(R.id.cardSiswa)
        val cardGuru = findViewById<CardView>(R.id.cardGuru)
        val cardJadwal = findViewById<CardView>(R.id.cardjadwal)

        // Atur visibilitas CardView sesuai role
        if (role == "guru") {
            cardGuru.visibility = View.GONE
        } else {
            cardGuru.visibility = View.VISIBLE
        }

        Log.d("MainActivity", "Role=$role, Username=$username")


        // Klik CardView untuk navigasi
        cardSiswa.setOnClickListener {
            startActivity(Intent(this, SiswaActivity::class.java))
        }

        cardGuru.setOnClickListener {
           startActivity(Intent(this, TeacherActivity::class.java))
        }

        cardJadwal.setOnClickListener {
//            startActivity(Intent(this, SchedulesActivity::class.java))
        }
    }
}
