package com.corsuevisionplus.tamazuj.fragments

import androidx.fragment.app.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.corsuevisionplus.tamazuj.R
import com.corsuevisionplus.tamazuj.models.Location

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapTamazujFragment : Fragment() {
        private var placeList: ArrayList<Location> = ArrayList()
    private val callback = OnMapReadyCallback { googleMap ->
        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */


        for(i in 0 until placeList.size) {

            val sydney = LatLng(placeList[i].lat, placeList[i].long)
            googleMap.addMarker(MarkerOptions().position(sydney).title(placeList[i].name))
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
            googleMap.animateCamera(CameraUpdateFactory.zoomBy(13.0F))
        }
    }

        override fun onCreateView(inflater: LayoutInflater,
                                  container: ViewGroup?,
                                  savedInstanceState: Bundle?): View? {
            return inflater.inflate(R.layout.fragment_map_tamazuj, container, false)
        }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)
            val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
            mapFragment?.getMapAsync(callback)
            placeList.add(Location(1, "Tamazuj", 29.9629845,31.2463595))
        }

    }