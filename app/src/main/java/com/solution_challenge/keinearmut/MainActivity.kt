package com.solution_challenge.keinearmut

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sign_up)

        if (isLoggedIn()) {
            startActivity(Intent(this, dashboard::class.java))
            finish() // Finish current activity
        } else {
            Toast.makeText(this, "Please login to continue", Toast.LENGTH_SHORT).show()
        }
    }

    private fun isLoggedIn(): Boolean {
        val sharedPreferences = getSharedPreferences("login_state", MODE_PRIVATE)
        return sharedPreferences.getBoolean("isLoggedIn", false)
    }

}
