package com.solution_challenge.keinearmut

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import android.widget.EditText
import android.Manifest
import android.app.AlertDialog
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationManagerCompat
import android.provider.Settings
import android.net.Uri
import android.widget.TextView
import android.text.SpannableStringBuilder
import android.text.SpannableString
import android.text.method.LinkMovementMethod
import android.view.View
import android.text.style.ClickableSpan

class login : ComponentActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    val RC_SIGN_IN = 9001


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("980841101419-mus4beag1ququg6mo496j1u0ts62oleb.apps.googleusercontent.com")
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)
        val signinbutton = findViewById<SignInButton>(R.id.googleSignInButton)
        signinbutton.setOnClickListener {
            val signInIntent = googleSignInClient.signInIntent
            startActivityForResult(signInIntent, RC_SIGN_IN)
        }

        auth = FirebaseAuth.getInstance()
        val emailEditText = findViewById<EditText>(R.id.editTextText2)
        val passwordEditText = findViewById<EditText>(R.id.editTextTextPassword2)
        val signInButton = findViewById<Button>(R.id.loginButton_loginPage)

        signInButton.setOnClickListener {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()
            signInWithEmail(email, password)
        }
        val spannableString = SpannableString(getString(R.string.text_with_clickable_span))
        val clickableText = "Sign up"
        val start = spannableString.indexOf(clickableText)
        val textViewClickable = findViewById<TextView>(R.id.signup_page)
        if (start != -1) {
            val end = start + clickableText.length
            val clickableSpan = object : ClickableSpan() {
                override fun onClick(widget: View) {
                    startActivity(Intent(this@login, sign_up::class.java))
                }
            }
            spannableString.setSpan(clickableSpan, start, end, SpannableStringBuilder.SPAN_EXCLUSIVE_EXCLUSIVE)
            textViewClickable.text = spannableString
            textViewClickable.movementMethod = LinkMovementMethod.getInstance()
        } else {
            Log.e(TAG, "Clickable text not found in string resource")
        }
        saveLoginState()
    }
    private fun signInWithEmail(email: String, password: String) {
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please enter email and password", Toast.LENGTH_SHORT).show()
            return
        }
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    navigateToNewActivity()
                } else {
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    Toast.makeText(this, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                }
            }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)


        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }
    fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(Exception::class.java)!!
            val firebaseAuth = FirebaseAuth.getInstance()
            val authCredential = GoogleAuthProvider.getCredential(account.idToken, null)
            firebaseAuth.signInWithCredential(authCredential)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        val user = firebaseAuth.currentUser
                        user?.let {
                            val userPreferences = UserPreferences(this)
                            val username = it.displayName
                            userPreferences.saveUsername(username ?: "")
                            val email = it.email
                            userPreferences.saveEmail(email ?: "")
                            val uid = it.uid
                            userPreferences.saveUid(uid ?: "")
                            val profilePhotoUrl = it.photoUrl.toString()
                            userPreferences.saveProfileUrl(profilePhotoUrl)
                        }
                        navigateToNewActivity()
                    } else {
                        Log.w(TAG, "signInWithEmail:failure", task.exception)
                        Toast.makeText(this, "Sign in failed", Toast.LENGTH_SHORT).show()
                    }
                }
        } catch (e: Exception) {
            Toast.makeText(this, "Sign in failed", Toast.LENGTH_SHORT).show()
        }
    }

   fun navigateToNewActivity() {
        startActivity(Intent(this, dashboard::class.java))
        finish()
    }
    companion object {
        private const val TAG = "LoginActivity"
    }
    private fun saveLoginState() {
        val sharedPreferences = getSharedPreferences("login_state", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean("isLoggedIn", true)
        editor.apply()
    }

}