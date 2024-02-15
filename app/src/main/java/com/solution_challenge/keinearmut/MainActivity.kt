package com.solution_challenge.keinearmut

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.google.firebase.auth.FirebaseAuth
import android.content.Context

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Thread.sleep(500)
        setContentView(R.layout.sign_up)

        val loginTextView: TextView = findViewById(R.id.myClickableText)
        loginTextView.setOnClickListener(this)

        if (isLoggedIn()) {
            startActivity(Intent(this, dashboard::class.java))
            finish()
        }else{
            startActivity(Intent(this, login::class.java))
            finish()

        }

    }

    private fun isLoggedIn(): Boolean {
        val sharedPreferences = getSharedPreferences("login_state", MODE_PRIVATE)
        return sharedPreferences.getBoolean("isLoggedIn", false)
    }


    override fun onClick(view: View) {
        if (view.id == R.id.myClickableText) {
            val intent = Intent(this, login::class.java)
            startActivity(intent)
        }
    }

}
