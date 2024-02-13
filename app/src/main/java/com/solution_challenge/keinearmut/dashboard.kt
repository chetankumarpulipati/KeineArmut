package com.solution_challenge.keinearmut

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.bumptech.glide.Glide
import com.google.android.material.navigation.NavigationView

class dashboard : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var drawerLayout: DrawerLayout

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

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, HomeFragment()).commit()
            navigationView.setCheckedItem(R.id.nav_home)
        }

        val userPreferences = UserPreferences(this)
        val headerView = navigationView.getHeaderView(0)

        val profilePhotoUrl = userPreferences.getProfileUrl()
        Log.d("ProfileURL", "Profile photo URL: $profilePhotoUrl")
        val profilePhotoImageView = headerView.findViewById<ImageView>(R.id.google_profile_image)
        Glide.with(this)
            .load(profilePhotoUrl) // Assuming you have a function to retrieve the profile photo URL from SharedPreferences
            .placeholder(R.drawable.bill_gates) // Placeholder image while loading
            .error(R.drawable.error) // Error image if loading fails
            .into(profilePhotoImageView)

        try{
            val textViewUsername = headerView.findViewById<TextView>(R.id.username_nav_header)
            textViewUsername.text = userPreferences.getUsername()
            val textViewEmail = headerView.findViewById<TextView>(R.id.email)
            textViewEmail.text = userPreferences.getEmail()
            val textViewUid = headerView.findViewById<TextView>(R.id.uid)
            textViewUid.text = userPreferences.getUid()
        } catch (e: Exception) {
            // Handle the specific exceptions that may occur
            when (e) {
                is NullPointerException -> {
                    // Handle NullPointerException
                    Log.e(TAG, "Null pointer exception occurred: ${e.message}")
                    // Optionally, provide user feedback or perform error recovery
                }
                is IllegalStateException -> {
                    // Handle IllegalStateException
                    Log.e(TAG, "Illegal state exception occurred: ${e.message}")
                    // Optionally, provide user feedback or perform error recovery
                }
                else -> {
                    // Handle other exceptions
                    Log.e(TAG, "An exception occurred: ${e.message}")
                    // Optionally, provide user feedback or perform error recovery
                }
            }
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_home -> supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, HomeFragment()).commit()
            R.id.nav_settings -> supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, SettingsFragment()).commit()
            R.id.nav_share -> supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, ShareFragment()).commit()
            R.id.nav_about -> supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, AboutFragment()).commit()
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
    companion object{
        private const val TAG = "DashboardActivity"
    }

}