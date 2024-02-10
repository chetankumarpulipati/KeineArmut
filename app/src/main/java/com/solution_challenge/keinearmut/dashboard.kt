package com.solution_challenge.keinearmut

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView

class dashboard: AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dashboard)

        val userPreferences = UserPreferences(this)
        val textViewUsername = findViewById<TextView>(R.id.username)
        val textViewEmail = findViewById<TextView>(R.id.email)
        val textViewuid = findViewById<TextView>(R.id.uid)
        val username = userPreferences.getUsername()
        val email = userPreferences.getEmail()
        val uid = userPreferences.getUid()

        if (username.isNotEmpty() || email.isNotEmpty() || uid.isNotEmpty()) {
            val userName = username
            textViewUsername.text = userName
            val emailId = email
            textViewEmail.text = emailId
            val uid = uid
            textViewuid.text = uid

        } else {
            Toast.makeText(this, "Unable to get Credential", Toast.LENGTH_SHORT).show()
        }
    }

}