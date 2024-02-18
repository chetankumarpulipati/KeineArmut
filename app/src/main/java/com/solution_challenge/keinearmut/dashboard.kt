package com.solution_challenge.keinearmut

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.bumptech.glide.Glide
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import android.content.Context
import android.widget.Toast

class dashboard: AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var firebaseAuth: FirebaseAuth

    companion object {
        private const val TAG = "DashboardActivity"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dashboard)
        drawerLayout = findViewById(R.id.drawer_layout)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        val navigationView = findViewById<NavigationView>(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)

        firebaseAuth = FirebaseAuth.getInstance()

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, HomeFragment()).commit()
            navigationView.setCheckedItem(R.id.nav_home)
        }

        val userPreferences = UserPreferences(this)
        val headerView = navigationView.getHeaderView(0)

        val profilePhotoUrl = userPreferences.getProfileUrl()
        Glide.with(this)
            .load(profilePhotoUrl)
            .placeholder(R.drawable.bill_gates)
            .error(R.drawable.error)
            .into(headerView.findViewById<ImageView>(R.id.google_profile_image))

        try {
            headerView.findViewById<TextView>(R.id.username_nav_header).text = userPreferences.getUsername()
            headerView.findViewById<TextView>(R.id.email).text = userPreferences.getEmail()
            headerView.findViewById<TextView>(R.id.uid).text = userPreferences.getUid()
        } catch (e: Exception) {
            Log.e(TAG, "An exception occurred: ${e.message}")
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_home -> supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, HomeFragment()).commit()
            R.id.nav_settings -> supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, SettingsFragment()).commit()
            R.id.nav_ai_assistant -> supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, AiAssistantFragment()).commit()
            R.id.nav_feedback -> supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, feedbackFragment()).commit()
            R.id.nav_logout -> {
                logout()
                navigateToSignUp()
            }
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }
    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
    fun saveLogoutState() {
        val sharedPreferences = getSharedPreferences("logout_state", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean("isLoggedOut",true)
        editor.apply()
    }
    private fun logout(): Boolean {
        firebaseAuth.signOut()
        saveLogoutState()
        Toast.makeText(this, "Logged out", Toast.LENGTH_SHORT).show()
        return true
    }

    private fun navigateToSignUp() {
        val intent = Intent(this, sign_up::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }

}
