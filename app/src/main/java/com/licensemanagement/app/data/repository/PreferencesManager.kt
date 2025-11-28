package com.licensemanagement.app.data.repository

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton
import com.licensemanagement.app.util.Constants

@Singleton
class PreferencesManager @Inject constructor(
    @ApplicationContext context: Context
) {
    private val prefs: SharedPreferences = context.getSharedPreferences(
        Constants.PREF_NAME,
        Context.MODE_PRIVATE
    )

    fun saveToken(token: String) {
        prefs.edit().putString(Constants.KEY_AUTH_TOKEN, token).apply()
    }

    fun getToken(): String? {
        return prefs.getString(Constants.KEY_AUTH_TOKEN, null)
    }

    fun saveApiKey(apiKey: String) {
        prefs.edit().putString(Constants.KEY_API_KEY, apiKey).apply()
    }

    fun getApiKey(): String? {
        return prefs.getString(Constants.KEY_API_KEY, null)
    }

    fun saveUserRole(role: String) {
        prefs.edit().putString(Constants.KEY_USER_ROLE, role).apply()
    }

    fun getUserRole(): String? {
        return prefs.getString(Constants.KEY_USER_ROLE, null)
    }

    fun saveUserEmail(email: String) {
        prefs.edit().putString(Constants.KEY_USER_EMAIL, email).apply()
    }

    fun getUserEmail(): String? {
        return prefs.getString(Constants.KEY_USER_EMAIL, null)
    }

    fun saveUserName(name: String) {
        prefs.edit().putString(Constants.KEY_USER_NAME, name).apply()
    }

    fun getUserName(): String? {
        return prefs.getString(Constants.KEY_USER_NAME, null)
    }

    fun clearAll() {
        prefs.edit().clear().apply()
    }
}

