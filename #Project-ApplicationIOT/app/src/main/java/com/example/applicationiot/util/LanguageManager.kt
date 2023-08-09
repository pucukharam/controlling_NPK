package com.example.applicationiot.util

import android.content.Context
import android.content.res.Resources
import android.os.Build
import java.util.Locale

class LanguageManager(private var context: Context) {

    companion object {
        var language = ""

    }

    init {
        language = SessionManager.getLanguage(context)
    }



    private fun getLang() = context.resources.configuration.locales.get(0).toString()

    fun wrap(): Context {
        if (language != "") {
            change(language)
        }
        return context
    }

    fun change(lang: String, callback: ((Resources) -> Unit) = {}) {
        val config = context.resources.configuration
        val locale = Locale(lang)
        Locale.setDefault(locale)
        config.setLocale(locale)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
            context = context.createConfigurationContext(config)
        } else {
            context.resources.updateConfiguration(config, context.resources.displayMetrics);
        }
        SessionManager.setLang(context, lang)
        callback(context.resources)
    }
}