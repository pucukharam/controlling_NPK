package com.example.applicationiot.activity

import android.os.Bundle
import com.example.applicationiot.databinding.ActivityRegisterBinding
import com.example.applicationiot.model.UserModel
import com.example.applicationiot.util.BaseAppCompatActivity
import com.example.applicationiot.util.SessionManager
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class RegistrationActivity : BaseAppCompatActivity() {
    var mDatabase: DatabaseReference? = null
    private lateinit var binding: ActivityRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if(SessionManager.getIsLogin(this)){
            goToPageAndClearStack(HomeActivity::class.java)
        }
        binding.tvLogin.setOnClickListener {
            goToPage(LoginActivity::class.java)
        }
        mDatabase = FirebaseDatabase.getInstance().reference
        val dbUsers = mDatabase!!.child("users")
        var userModel = UserModel()
        binding.tvRegistration.setOnClickListener {
            if (!binding.etName.checkValue()) {
                return@setOnClickListener
            }
            userModel.firstName= binding.etName.getValue()
            userModel.lastName= binding.etName.getValue()

            if (!binding.etEmail.checkValue()) {
                return@setOnClickListener
            }
            userModel.email= binding.etEmail.getValue()

            if (!binding.etPhoneNumber.checkValue()) {
                return@setOnClickListener
            }
            userModel.phoneNumber= binding.etPhoneNumber.getValue()

            if (!binding.etPassword.checkValue()) {
                return@setOnClickListener
            }
            userModel.password= binding.etPassword.getValue()
            val id: String = dbUsers.push().key ?:""
            userModel.userId= id
            dbUsers.child(id).setValue(userModel)
            goToPageAndClearStack(LoginActivity::class.java)
        }
    }
}