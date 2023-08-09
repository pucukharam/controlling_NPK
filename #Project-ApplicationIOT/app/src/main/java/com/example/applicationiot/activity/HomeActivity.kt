package com.example.applicationiot.activity

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.applicationiot.R
import com.example.applicationiot.databinding.ActivityHomeBinding
import com.example.applicationiot.fragment.HomeFragment
import com.example.applicationiot.fragment.LocationFragment
import com.example.applicationiot.fragment.ProfileFragment
import com.example.applicationiot.util.BaseAppCompatActivity

class HomeActivity: BaseAppCompatActivity() {
    private var fragmentManager: FragmentManager? = null



    private lateinit var binding : ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        fragmentManager = supportFragmentManager
        fragmentManager?.beginTransaction()?.replace(R.id.main_container, HomeFragment.newInstance(), HomeFragment.TAG)?.commit()
        binding.btmNavigation.setOnItemSelectedListener { item ->
            if (item.itemId == R.id.navigation_satu) {
                commitFragment(HomeFragment.TAG)
            } else if (item.itemId == R.id.navigation_dua) {
                commitFragment(LocationFragment.TAG)
            } else if (item.itemId == R.id.navigation_tiga) {
                commitFragment(ProfileFragment.TAG)
            } else {
                commitFragment(ProfileFragment.TAG)
            }
            true
        }
    }
    private fun commitFragment(tag: String) {
        var fragment: Fragment?
        fragment = fragmentManager!!.findFragmentByTag(tag)
        if (fragment == null) {
            fragment = if (tag == HomeFragment.TAG) {
                HomeFragment.newInstance()
            } else if (tag == LocationFragment.TAG) {
                LocationFragment.newInstance()
            } else if (tag == ProfileFragment.TAG) {
                ProfileFragment.newInstance()
            } else {
                ProfileFragment.newInstance()
            }
        }
        fragmentManager?.beginTransaction()?.replace(R.id.main_container, fragment!!, tag)?.commit()
    }

    override fun onResume() {
        super.onResume()
    }
}