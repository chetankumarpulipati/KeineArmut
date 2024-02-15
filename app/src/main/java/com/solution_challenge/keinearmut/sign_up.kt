package com.solution_challenge.keinearmut

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException


class sign_up : AppCompatActivity() {

    // signup
    private lateinit var auth: FirebaseAuth
    private lateinit var fullname: EditText
    private lateinit var signupemail: EditText
    private lateinit var signupmobile: EditText
    private lateinit var signuppassword: EditText
    private lateinit var button_sign_up: Button
    private lateinit var progressBar: ProgressBar
    private lateinit var userPreferences: UserPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sign_up)
        fullname = findViewById(R.id.fullname)
        signupemail = findViewById(R.id.signupemail)
        signupmobile = findViewById(R.id.signupmobile)
        signuppassword = findViewById(R.id.signuppassword)
        button_sign_up = findViewById(R.id.button_sign_up)
        auth = FirebaseAuth.getInstance()
        progressBar = findViewById(R.id.progressBar)
        userPreferences = UserPreferences(this)

        val loginTextView: TextView = findViewById(R.id.myClickableText)

        loginTextView.setOnClickListener {
            val intent = Intent(this, login::class.java)
            startActivity(intent)
            finish()
        }

        button_sign_up.setOnClickListener {
            val fullName = fullname.text.toString()
            val email = signupemail.text.toString()
            val password = signuppassword.text.toString()

            userPreferences.saveUserName(fullName)
            userPreferences.saveRegEmail(email)
//            Log.d("username_store_check",fullName)
//            Log.d("useremail_store_check",email)

            if (!isValidEmail(email)) {
                signupemail.error = "Enter valid email address"
                return@setOnClickListener
            }
            if (!isValidPassword(password)) {
                signuppassword.error = "Password is required"
                return@setOnClickListener
            }
            progressBar.visibility = View.VISIBLE

            signUp(email, password)

        }
        if(isLoggedOut()){
            Toast.makeText(this, "You have been logged out", Toast.LENGTH_SHORT).show()
        }

    }
    private fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
    fun isValidPassword(password: String): Boolean {
        val passwordRegex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#\$%^&+=!])(?=\\S+\$).{8,}\$".toRegex()
        return passwordRegex.matches(password)
    }
    private fun signUp(email:String, password: String) {
        progressBar.visibility = View.VISIBLE
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    saveLoginState()
                    navigateToNewActivity()
                } else {
                    if (task.exception is FirebaseAuthUserCollisionException) {
                        Toast.makeText(
                            this,
                            "This email is already registered.",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        if (task.exception is FirebaseAuthUserCollisionException) {
                            Toast.makeText(this, "This email is already registered.", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(baseContext, "Sign up failed. Try again after some time.", Toast.LENGTH_SHORT).show()
                        }
                        progressBar.visibility = View.GONE
                    }
            }
    }
}
    public override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if (currentUser != null) {
            reload()
        }
    }
    private fun reload() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
    fun navigateToNewActivity() {
        startActivity(Intent(this, dashboard::class.java))
        finish()
    }

    private fun intentOfFailure(task: Task<AuthResult>){
        Toast.makeText(this, "Sign up failed. ${task.exception?.message}", Toast.LENGTH_SHORT).show()
        reload()
    }
    private fun saveLoginState() {
        val sharedPreferences = getSharedPreferences("login_state", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean("isLoggedIn", true)
        editor.apply()
    }
    companion object {
        private const val TAG = "sign_up"
    }
    private fun isLoggedOut(): Boolean {
        val sharedPreferences = getSharedPreferences("login_state", MODE_PRIVATE)
        return sharedPreferences.getBoolean("isLoggedOut", false)
    }
}
