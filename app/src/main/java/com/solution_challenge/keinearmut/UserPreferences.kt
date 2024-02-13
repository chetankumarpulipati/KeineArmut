package com.solution_challenge.keinearmut

import android.content.Context
import android.content.SharedPreferences
import android.util.Log


class UserPreferences(context: Context){
    companion object {
        private const val PREF_NAME = "UserProfile"
        private const val KEY_PROFILE_URL = "profileUrl"
    }
    val sharedPreferences: SharedPreferences = context.getSharedPreferences("UserPreferences", Context.MODE_PRIVATE)
    fun saveUsername(username: String){
        sharedPreferences.edit().putString("username", username).apply()
    }
    fun getUsername(): String{
        return sharedPreferences.getString("username", "")!!
    }
    fun saveEmail(email: String){
        sharedPreferences.edit().putString("email", email).apply()
    }
    fun getEmail(): String{
        return sharedPreferences.getString("email", "")!!
    }
    fun saveUid(uid :String){
        sharedPreferences.edit().putString("uid", uid).apply()
    }
    fun getUid(): String{
        return sharedPreferences.getString("uid", "")!!
    }
    fun saveProfileUrl(profileUrl: String) {
//        Log.d("UserPreferences", "Saving profile URL: $profileUrl")
        sharedPreferences.edit().putString(KEY_PROFILE_URL, profileUrl).apply()
    }

    fun getProfileUrl(): String? {
        return sharedPreferences.getString(KEY_PROFILE_URL, null)
    }

    fun saveRegEmail(email: String){
        sharedPreferences.edit().putString("regEmail", email).apply()
    }
    fun getRegEmail(): String{
        return sharedPreferences.getString("regEmail", "")!!
    }


}