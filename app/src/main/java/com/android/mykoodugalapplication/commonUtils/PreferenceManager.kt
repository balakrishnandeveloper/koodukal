package com.android.mykoodugalapplication.commonUtils

import android.content.Context
import android.content.SharedPreferences

class PreferenceManager(context: Context) {

    companion object {

        private const val PREF_NAME = "KoodugalPreference"

        private const val USER_ID = "user_id"
        private const val TOKEN = "token"
        private const val USER_NAME = "user_name"
        private const val EMAIL = "email"
        private const val MOBILE = "mobile"
        private const val IS_LOGIN = "is_login"
    }

    private val preferences: SharedPreferences =
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    // Save Login Data
    fun saveLoginData(
        userId: String,
        token: String,
        name: String,
        email: String,
        mobile: String
    ) {

        preferences.edit()
            .putString(USER_ID, userId)
            .putString(TOKEN, token)
            .putString(USER_NAME, name)
            .putString(EMAIL, email)
            .putString(MOBILE, mobile)
            .putBoolean(IS_LOGIN, true)
            .apply()
    }

    // User Id
    fun getUserId(): String {
        return preferences.getString(USER_ID, "") ?: ""
    }

    // Token
    fun getToken(): String {
        return preferences.getString(TOKEN, "") ?: ""
    }

    // Name
    fun getUserName(): String {
        return preferences.getString(USER_NAME, "") ?: ""
    }

    // Email
    fun getEmail(): String {
        return preferences.getString(EMAIL, "") ?: ""
    }

    // Mobile
    fun getMobile(): String {
        return preferences.getString(MOBILE, "") ?: ""
    }

    // Login Status
    fun isLoggedIn(): Boolean {
        return preferences.getBoolean(IS_LOGIN, false)
    }

    // Logout / Clear
    fun clearSession() {
        preferences.edit().clear().apply()
    }
}