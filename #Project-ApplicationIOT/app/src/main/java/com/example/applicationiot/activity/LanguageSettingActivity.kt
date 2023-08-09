package com.example.applicationiot.activity

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import com.example.applicationiot.R
import com.example.applicationiot.databinding.ActivityLanguageSettingBinding
import com.example.applicationiot.util.BaseAppCompatActivity
import com.example.applicationiot.util.LanguageManager
import com.example.applicationiot.util.SessionManager
import java.util.Locale

class LanguageSettingActivity : BaseAppCompatActivity() {
    private lateinit var binding: ActivityLanguageSettingBinding
    private lateinit var languageManager: LanguageManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLanguageSettingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.ivBack.setOnClickListener {
            onBackPressed()
        }

        languageManager =LanguageManager(this)
        if(SessionManager.getLanguage(this)=="in"){
            binding.rbIn.isChecked=true
        }else{
            binding.rbEng.isChecked=true
        }
        binding.rg.setOnCheckedChangeListener { _, i ->
            if (i == R.id.rbEng) {
                languageManager.change("en")
            } else if (i == R.id.rbIn) {
                languageManager.change("in")
            }
            changeLanguage()
        }
    }
    private fun changeLanguage(){
        goToPageAndClearStack(HomeActivity::class.java)
        showToast(getString(R.string.language_changed))
    }
}