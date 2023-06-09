package com.skythecodemaster.singularity

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import com.google.android.gms.location.LocationRequest
import android.os.Bundle
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices

class GPSFragment : Fragment() {
  private lateinit var fusedLocationClient: FusedLocationProviderClient
  private lateinit var locationCallback: LocationCallback
  
  // this is our view (replacing getViewById)
  private lateinit var layout: View
  
  
  @SuppressLint("SetTextI18n", "MissingPermission", "VisibleForTests")
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    
    fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity!!.applicationContext)
  
    fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
      run {
        val tv1: TextView = layout.findViewById(R.id.txt_gps)
        val tv2: TextView = layout.findViewById(R.id.txt_alt)
        if (location != null) {
          tv1.text = location.latitude.toString() + "," + location.longitude.toString()
          tv2.text = "Alt: " + location.altitude + "m"
        } else {
          tv1.text = "dunno bro"
          tv2.text = "lol nope"
        }
      }
    }

    val locationRequest: LocationRequest? = LocationRequest.create()?.apply {
      interval = 5000
      fastestInterval = 2500
      priority = LocationRequest.PRIORITY_HIGH_ACCURACY
    }
    
    locationCallback = object : LocationCallback() {
      override fun onLocationResult(locationResult: LocationResult?) {
        locationResult?: return
        val tv1: TextView = layout.findViewById(R.id.txt_gps)
        val location: Location = locationResult.lastLocation
        tv1.text = location.latitude.toString() + "," + location.longitude.toString()
        val tv2: TextView = layout.findViewById(R.id.txt_alt)
        tv2.text = "Alt: " + location.altitude + "m"
      }
    }
    
    fusedLocationClient.requestLocationUpdates(
      locationRequest, locationCallback, Looper.getMainLooper()
    )
  }
  
  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    
    // Inflate the layout for this fragment
    layout = inflater.inflate(R.layout.fragment_gps, container, false)
    return layout
  }
}