package com.example.applicationiot.activity

import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.applicationiot.databinding.ActivityNotificationBinding
import com.example.applicationiot.model.PredictionModel
import com.example.applicationiot.util.BaseAppCompatActivity
import com.example.applicationiot.util.NotificationAdapter
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class NotificationActivity : BaseAppCompatActivity() {
    var mDatabase: DatabaseReference? = null
    private lateinit var binding: ActivityNotificationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotificationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.ivBack.setOnClickListener {
            onBackPressed()
        }
        binding.rvNotification.layoutManager = LinearLayoutManager(this)
        val adapter = NotificationAdapter(this)
        binding.rvNotification.adapter = adapter
        val data = arrayListOf<String>()
        adapter.addData(data)
        mDatabase = FirebaseDatabase.getInstance().reference
        val dbPrediksi = mDatabase!!.child("prediksi")
        dbPrediksi.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val predictionModel: PredictionModel? =
                    dataSnapshot.getValue(PredictionModel::class.java)
                predictionModel?.let {
                    Log.d("notification ","--> ${it.word
                        .replace("(", "")
                        .replace(")", "")
                        .replace(",","")
                        .replace("'","")}")
                    val content = it.word
                        .replace("(", "")
                        .replace(")", "")
                        .replace(",","")
                        .replace("'","")
                    data.add(content)
                    adapter.addData(data)
                }

            }

            override fun onCancelled(databaseError: DatabaseError) {
               showToast(databaseError.message)
            }
        })
    }
}