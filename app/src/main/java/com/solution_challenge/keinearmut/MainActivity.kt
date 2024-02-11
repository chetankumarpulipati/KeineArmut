package com.solution_challenge.keinearmut

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen

class MainActivity : AppCompatActivity(), View.OnClickListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        Thread.sleep(500)
        setContentView(R.layout.sign_up)

        val loginTextView: TextView = findViewById(R.id.myClickableText)
        loginTextView.setOnClickListener(this)

        if (isLoggedIn()) {
            startActivity(Intent(this, dashboard::class.java))
            finish()
        } else {
//            Toast.makeText(this, "Please login to continue", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this,sign_up::class.java))
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
