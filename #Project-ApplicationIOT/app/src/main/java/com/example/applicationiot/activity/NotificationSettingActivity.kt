package com.example.applicationiot.activity

import android.os.Bundle
import com.example.applicationiot.R
import com.example.applicationiot.databinding.ActivityLanguageSettingBinding
import com.example.applicationiot.databinding.ActivityNotificationSettingBinding
import com.example.applicationiot.util.BaseAppCompatActivity
import com.example.applicationiot.util.SessionManager

class NotificationSettingActivity: BaseAppCompatActivity() {
    private lateinit var binding : ActivityNotificationSettingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotificationSettingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.ivBack.setOnClickListener {
            onBackPressed()
        }
        if(SessionManager.getNotification(this)){
            binding.rbOn.isChecked=true
        }else{
            binding.rbOff.isChecked=true
        }
        binding.rg.setOnCheckedChangeListener { _, i ->
            if (i == R.id.rbOn) {
                SessionManager.setNotification(this, true)
            } else if (i == R.id.rbOff) {
                SessionManager.setNotification(this, false)
            }
        }
    }
}