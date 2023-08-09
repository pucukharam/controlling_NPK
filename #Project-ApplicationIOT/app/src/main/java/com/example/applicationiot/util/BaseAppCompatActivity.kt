package com.example.applicationiot.util

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

open class BaseAppCompatActivity : AppCompatActivity() {
    fun goToPage(cls: Class<*>?) {
        val intent = Intent(this, cls)
        startActivity(intent)
    }
    fun goToPageAndClearStack(cls: Class<*>?) {
        val intent = Intent(this, cls)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }
    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(newBase?.let { LanguageManager(it).wrap() })
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
    }
    fun showToast(message:String){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
    fun EditText.getValue()= this.text.toString()
    fun EditText.checkValue(): Boolean{
        if(this.text.isNullOrEmpty()){
            this.error="Please filled this"
            return false
        }
        return true
    }
}