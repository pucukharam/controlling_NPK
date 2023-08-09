package com.example.applicationiot.util

import android.content.Context
import android.content.SharedPreferences

object SessionManager {
    const val KEY_LANGUAGE = "language"
    const val KEY_LOGIN = "login"
    const val KEY_ID = "id"
    const val KEY_NAME = "name"
    const val KEY_EMAIL = "email"
    const val KEY_PHONE = "phone"
    const val KEY_NOTIFICATION = "notification"
    const val KEY_INITIALIZED_DEFAULT_DATA = "default_data"
    private fun getSharedPreference(context: Context): SharedPreferences {
        return context.getSharedPreferences(
            "pet_care", Context.MODE_PRIVATE
        )
    }

    fun setCustomerData(
        context: Context,
        id: String?,
        name: String?,
        phone: String?,
        email: String?,
    ) {
        val editor = getSharedPreference(context).edit()
        editor.putBoolean(KEY_LOGIN, true)
        editor.putBoolean(KEY_NOTIFICATION, true)
        editor.putString(KEY_NAME, name)
        editor.putString(KEY_PHONE, phone)
        editor.putString(KEY_EMAIL, email)
        editor.putString(KEY_ID, id)
        editor.apply()
    }

    fun setInitializedDefaultData(context: Context) {
        val editor = getSharedPreference(context).edit()
        editor.putBoolean(KEY_INITIALIZED_DEFAULT_DATA, true)
        editor.apply()
    }

    fun setNotification(context: Context, value : Boolean) {
        val editor = getSharedPreference(context).edit()
        editor.putBoolean(KEY_NOTIFICATION,value)
        editor.apply()
    }



    fun setLang(context: Context, lang: String?) {
        val editor = getSharedPreference(context).edit()
        editor.putString(KEY_LANGUAGE, lang)
        editor.apply()
    }

    fun getInitializedDefaultData(context: Context): Boolean {
        return getSharedPreference(context).getBoolean(KEY_INITIALIZED_DEFAULT_DATA, false)
    }

    fun getLanguage(context: Context): String {
        return getSharedPreference(context).getString(KEY_LANGUAGE, "")?:"in"
    }
    fun getNotification(context: Context): Boolean {
        return getSharedPreference(context).getBoolean(KEY_NOTIFICATION, true)
    }

    fun getId(context: Context): String? {
        return getSharedPreference(context).getString(KEY_ID, "")
    }

    fun getEmail(context: Context): String? {
        return getSharedPreference(context).getString(KEY_EMAIL, "")
    }

    fun getName(context: Context): String? {
        return getSharedPreference(context).getString(KEY_NAME, "")
    }

    fun getPhone(context: Context): String? {
        return getSharedPreference(context).getString(KEY_PHONE, "")
    }

    fun getIsLogin(context: Context): Boolean {
        return getSharedPreference(context).getBoolean(KEY_LOGIN, false)
    }

    fun clearData(context: Context) {
        val isDefaultDataInserted = getInitializedDefaultData(context)
        getSharedPreference(context).edit().clear().apply()
        if (isDefaultDataInserted) {
            setInitializedDefaultData(context)
        }
    }
}