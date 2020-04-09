package com.google.ar.sceneform.samples.hellosceneform

import android.content.Context
import android.content.SharedPreferences

object PreferencesUtils {

    private lateinit var sharedPreferences: SharedPreferences
    private const val preferences: String = "AR_BANANADEV_SETTINGS"

    const val LOGIN_KEY = "login_key"
    const val PASSWORD_KEY = "password_key"

    fun setSharedPreferences(context: Context) {
        sharedPreferences = context.getSharedPreferences(preferences, Context.MODE_PRIVATE)
    }

    fun getBoolean(key: String): Boolean {
        return sharedPreferences.getBoolean(key, false)
    }

    fun getInt(key: String): Int {
        return sharedPreferences.getInt(key, -1)
    }

    fun getString(key: String): String? {
        return sharedPreferences.getString(key, "")
    }

    fun saveInt(key: String, value: Int) {
        val editor = sharedPreferences.edit()
        editor.putInt(key, value)
        editor.apply()
    }

    fun saveString(key: String, value: String) {
        val editor = sharedPreferences.edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun saveBoolean(key: String, value: Boolean) {
        val editor = sharedPreferences.edit()
        editor.putBoolean(key, value)
        editor.apply()
    }

}