package com.example.firebasehp3

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.widget.ListView
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*

class MainActivity : AppCompatActivity() {

    lateinit var listView: ListView

    lateinit var ref: DatabaseReference
    lateinit var datalist: MutableList<MyData>
    private lateinit var layoutRv: RecyclerView
    private var datalisku: ArrayList<ListDataku> = arrayListOf()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        layoutRv = findViewById(R.id.rv_dataku)

        checkPermission()

    }

    override fun onStart() {
        super.onStart()
        checkPermission()
        loadData()
    }

    override fun onResume() {
        super.onResume()
        checkPermission()
        loadData()
    }

    fun checkPermission(){

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
           if (Build.VERSION.SDK_INT >= 23) {
                requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),0)
            }
            return
        }
    }
    private fun loadMePlease(){
        layoutRv.layoutManager = LinearLayoutManager(this)
        val myAdapter = MyAdapter(datalisku)
        layoutRv.adapter = myAdapter
    }

    fun loadData(){

        ref = FirebaseDatabase.getInstance().getReference("Users")

        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                if (p0!!.exists()){
                    datalisku.clear()
                    for (h in p0.children){
                        val mydataku = h.getValue(ListDataku::class.java)
                        datalisku.add(mydataku!!)
                    }
                }
                loadMePlease()

            }

            override fun onCancelled(p0: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

}
