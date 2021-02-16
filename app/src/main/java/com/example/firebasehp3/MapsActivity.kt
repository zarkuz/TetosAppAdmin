package com.example.firebasehp3

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.database.*

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private var refkuy: DatabaseReference? = null

    private var uid: String? = null
//    private var latiApp: String? = null
//    private var langiApp: String? = null
    private var latiApp: Double? = 0.0
    private var langiApp: Double? = 0.0
    private lateinit var fusedLoationClient: FusedLocationProviderClient
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.

        checkPermission()
        val bundle = intent.extras
        if (bundle!=null){
            uid = bundle.getString("uid")
        }
        fusedLoationClient = LocationServices.getFusedLocationProviderClient(this)
        refkuy = FirebaseDatabase.getInstance().getReference("Users")
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
        mMap = googleMap
        mMap.uiSettings.isZoomControlsEnabled = true
        checkPermission()
        mMap.isMyLocationEnabled = true


        var panggil = refkuy!!.child(uid.toString()).child("lokasiku").orderByKey().limitToLast(1)
        panggil.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snap: DataSnapshot) {
                var valku = snap.children
                var status=""
                valku.forEach{
                    var dataku = it.key.toString()
                    langiApp = snap.child(dataku).child("langiApp").value.toString().toDouble()
                    latiApp  = snap.child(dataku).child("latiApp").value.toString().toDouble()
                    status = dataku
                }
                if (status==""){
                    Toast.makeText( this@MapsActivity, "data tidak ditemukan", Toast.LENGTH_SHORT).show()
                } else {
                    var lokasiku = LatLng(langiApp!!, latiApp!!)
                    mMap.addMarker(MarkerOptions().position(lokasiku).title("Lokasi Terakhir Anda"))
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(lokasiku, 12f))
                }

            }
            override fun onCancelled(databaseError: DatabaseError) {}
        })

//        val lokasiku = LatLng(add1!!, add2!!)
    }
    fun checkPermission(){

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= 23) {
                requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),0)
            }
            return
        }
    }
}
