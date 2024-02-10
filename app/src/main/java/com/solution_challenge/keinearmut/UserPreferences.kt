package com.solution_challenge.keinearmut

import android.content.Context
import android.content.SharedPreferences

class UserPreferences(context: Context){
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

}