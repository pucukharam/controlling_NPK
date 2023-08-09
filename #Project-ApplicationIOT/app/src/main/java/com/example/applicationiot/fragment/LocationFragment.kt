package com.example.applicationiot.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.applicationiot.R
import com.example.applicationiot.activity.NotificationActivity
import com.example.applicationiot.databinding.FragmentLocationBinding
import com.example.applicationiot.util.BaseAppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.MarkerOptions


class LocationFragment: Fragment() {
    private lateinit var binding: FragmentLocationBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLocationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val supportMapFragment =
            childFragmentManager.findFragmentById(R.id.google_map) as SupportMapFragment?
        supportMapFragment!!.getMapAsync { googleMap ->
            // When map is loaded
            googleMap.setOnMapClickListener { latLng -> // When clicked on map
                val markerOptions = MarkerOptions()
                markerOptions.position(latLng)
                markerOptions.title(latLng.latitude.toString() + " : " + latLng.longitude)
                googleMap.clear()
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10f))
                googleMap.addMarker(markerOptions)
            }

        }
        binding.ivNotification.setOnClickListener {
            ( requireActivity() as BaseAppCompatActivity).goToPage(NotificationActivity::class.java)

        }
    }

    companion object {
        fun newInstance(): LocationFragment{
            val fragment = LocationFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
        const val TAG="location"

    }
}