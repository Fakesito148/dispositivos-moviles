package com.example.dismov.utils

import android.content.Context
import android.content.SharedPreferences

object TokenManager {
    private const val PREFS_NAME = "my_prefs"
    private const val TOKEN_KEY = "auth_token"
    private lateinit var prefs: SharedPreferences

    fun init(context: Context) {
        prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    fun saveToken(token: String) {
        prefs.edit().putString(TOKEN_KEY, token).apply()
    }

    fun getToken(): String? {
        return prefs.getString(TOKEN_KEY, null)
    }

    fun clearToken() {
        prefs.edit().remove(TOKEN_KEY).apply()
    }
}
