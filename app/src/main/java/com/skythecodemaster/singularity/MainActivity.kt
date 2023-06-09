package com.skythecodemaster.singularity

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import com.google.android.gms.location.LocationRequest
import android.os.Bundle
import android.os.Looper
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
  
  lateinit var bottomNav : BottomNavigationView
  
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
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
    loadFragment(MainFragment())
    bottomNav = findViewById(R.id.bottomNav) as BottomNavigationView
    bottomNav.setOnItemSelectedListener {
      when (it.itemId) {
        R.id.main -> {
          loadFragment(MainFragment())
          true
        }
        R.id.gps -> {
          loadFragment(GPSFragment())
          true
        }
        /*R.id.settings -> {
          loadFragment(SettingsFragment())
          true
        }*/
        else -> {
          loadFragment(MainFragment())
            true
        }
      }
    }
  }
  private  fun loadFragment(fragment: Fragment){
    val transaction = supportFragmentManager.beginTransaction()
    transaction.replace(R.id.container,fragment)
    transaction.commit()
  }
}