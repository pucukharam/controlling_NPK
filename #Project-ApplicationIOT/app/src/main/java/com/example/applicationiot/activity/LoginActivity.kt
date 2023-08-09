package com.example.applicationiot.activity

import android.os.Bundle
import com.example.applicationiot.databinding.ActivityLoginBinding
import com.example.applicationiot.model.UserModel
import com.example.applicationiot.util.BaseAppCompatActivity
import com.example.applicationiot.util.SessionManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class LoginActivity : BaseAppCompatActivity() {
    private var mDatabase: DatabaseReference? = null

    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.tvLogin.setOnClickListener {
            if (!binding.etEmail.checkValue()) {
                return@setOnClickListener
            }
            if (!binding.etPassword.checkValue()) {
                return@setOnClickListener
            }
            login(binding.etEmail.getValue(), binding.etPassword.getValue())

        }
        mDatabase = FirebaseDatabase.getInstance().reference

    }
    private fun login(email: String ,  password: String){
        val dbUsers = mDatabase!!.child("users")
        dbUsers.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (userSnapshot in dataSnapshot.children) {
                    val user: UserModel? = userSnapshot.getValue(UserModel::class.java)
                    if(user?.email==email && user.password== password ){
                        SessionManager.setCustomerData(
                            this@LoginActivity,
                            user.userId,
                            user.firstName,
                            user.phoneNumber,
                            user.email
                        )
                        goToPageAndClearStack(HomeActivity::class.java)
                        return
                    }
                }
                showToast("Email atau password salah")
            }

            override fun onCancelled(databaseError: DatabaseError) {
                showToast(databaseError.message)
            }
        })
    }
}