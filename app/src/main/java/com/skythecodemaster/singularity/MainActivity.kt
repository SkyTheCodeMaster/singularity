package com.skythecodemaster.singularity

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import com.google.android.gms.location.LocationRequest
import android.os.Bundle
import android.os.Looper
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices

class MainActivity : ComponentActivity() {
  private lateinit var fusedLocationClient: FusedLocationProviderClient
  private lateinit var locationCallback: LocationCallback
  
  
  @SuppressLint("SetTextI18n")
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    
    fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
  
    setContentView(R.layout.activity_gps)
    if(
      ActivityCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_COARSE_LOCATION)!= PackageManager.PERMISSION_GRANTED
      && ActivityCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED
      && ActivityCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_BACKGROUND_LOCATION)!= PackageManager.PERMISSION_GRANTED
    ) {
      ActivityCompat.requestPermissions(
        this,
        arrayOf(
          android.Manifest.permission.ACCESS_FINE_LOCATION,
          android.Manifest.permission.ACCESS_COARSE_LOCATION,
          android.Manifest.permission.ACCESS_BACKGROUND_LOCATION
        )
        ,1
      )
    }
  
    fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
      run {
        val tv1: TextView = findViewById(R.id.txt_gps)
        val tv2: TextView = findViewById(R.id.text_alt)
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
        val tv1: TextView = findViewById(R.id.txt_gps)
        val location: Location = locationResult.lastLocation
        tv1.text = location.latitude.toString() + "," + location.longitude.toString()
        val tv2: TextView = findViewById(R.id.text_alt)
        tv2.text = "Alt: " + location.altitude + "m"
      }
    }
    
    fusedLocationClient.requestLocationUpdates(
      locationRequest, locationCallback, Looper.getMainLooper()
    )
  }
}