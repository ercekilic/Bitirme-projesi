package com.example.mybitirmeproject

import android.Manifest
import android.content.Context
import android.location.LocationManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.mybitirmeproject.databinding.ActivityMainBinding
import com.google.android.gms.location.*
import pub.devrel.easypermissions.EasyPermissions

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        if (isLocationEnabled()){
            startLocationOperation()
        }
    }

    private fun startLocationOperation() {
        if (!EasyPermissions.hasPermissions(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
            EasyPermissions.requestPermissions(
                this,
                "Uygulamadan daha iyi verim alabilmek için konum izni vermelisiniz.",
                3,
                Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION
            )
            return
        }   else {

        }
    }




    private fun isLocationEnabled(): Boolean {
        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER)
    }

}