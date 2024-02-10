package com.solution_challenge.keinearmut

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sign_up)
        val loginTextView: TextView = findViewById(R.id.myClickableText)

        // Set OnClickListener for the login text
        loginTextView.setOnClickListener(this)

        // Check if the user is logged in
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

    override fun onClick(view: View) {
        // Check if the clicked view is the login text
        if (view.id == R.id.myClickableText) {
            // Start the LoginActivity
            val intent = Intent(this, login::class.java)
            startActivity(intent)
        }
    }

}
