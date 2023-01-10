package com.example.mybitirmeproject.kargomnerede

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.mybitirmeproject.R
import com.example.mybitirmeproject.databinding.ActivityKargomNeredeBinding

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.util.*

//import com.example.mybitirmeproject.kargomnerede.databinding.ActivityKargomNeredeBinding

class KargomNerede : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityKargomNeredeBinding
    var courierId = ""
    var lat = 0.0
    var long = 0.0



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
                if(KargomNeredeArgs.fromBundle(intent.extras as Bundle).courierId.isNullOrEmpty()){
                    courierId=KargomNeredeArgs.fromBundle(intent.extras as Bundle).courierId
                    Log.d("mapta kurye adı","$courierId")

                }
        if(courierId.isNullOrEmpty()){
        }

        binding = ActivityKargomNeredeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        courierId=KargomNeredeArgs.fromBundle(intent.extras as Bundle).courierId
        Log.d("mapta kurye adı","$courierId")
        mMap = googleMap
        val database= Firebase.database
        val myRef = database.getReference("Users")
        myRef.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (postSnapshot in snapshot.getChildren()){
                    Log.d("tata",postSnapshot.toString())
                    if(postSnapshot.key.equals(courierId)){
                        Log.d("tata2",postSnapshot.key.toString())
                        val lat = postSnapshot.child("lat").value as Double
                        val long = postSnapshot.child("long").value as Double
                        val er = LatLng(lat,long)
                        Log.d("tata3",lat.toString())
                        mMap.clear()

                        mMap.addMarker(MarkerOptions().position(er))
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(er))
                        mMap.setMinZoomPreference(10.0f);
                       // mMap.setMaxZoomPreference(14.0f);
                       // Timer().scheduleAtFixedRate(object : TimerTask() {
                        //     override fun run() {
                        //         val lat = postSnapshot.child("lat").value as Double
                        //         val long = postSnapshot.child("long").value as Double
                        //         val er = LatLng(lat,long)
                        //       Log.d("lat",lat.toString())
//


                        //    }
                        // }, 0, 5000)

                    }
                }}

            override fun onCancelled(error: DatabaseError) {

            }

        })

        //val sydney = LatLng(40.2141054, 16.6796558)
        //mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }
}