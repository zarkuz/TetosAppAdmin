package com.example.firebasehp3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main2.*

class Main2Activity : AppCompatActivity() {

    private var refkuy: DatabaseReference? = null
    private var uidkuy: String? = null
    private var langiAppsen: String? = null
    private var latiAppsen: String? = null
//    uiku
    private var tvnik: TextView? = null
    private var tvnama: TextView? = null
    private var tvalamat: TextView? = null
    private var tvemail: TextView? = null
    private var btnlihat: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        inisialisasi()
        refkuy = FirebaseDatabase.getInstance().getReference("Users")

//
//        var panggil = refkuy!!.child(mydata.uidku).child("lokasiku").orderByKey().limitToLast(1)
//        panggil.addListenerForSingleValueEvent(object : ValueEventListener {
//            override fun onDataChange(snap: DataSnapshot) {
//                var valku = snap.children
//                valku.forEach{
//                    var dataku = it.key.toString()
//                    langiAppsen = snap.child(dataku).child("langiApp").value.toString()
//                    latiAppsen = snap.child(dataku).child("latiApp").value.toString()
//                }
//            }
//            override fun onCancelled(databaseError: DatabaseError) {}
//        })
        if (intent.extras != null){
            val terima = intent.extras
            uidkuy = terima?.getString("uidku")
        }

        var panggil = refkuy!!.child(uidkuy!!)
        panggil.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                tvnik!!.text = " : "+ snapshot.child("nik").value as String
                tvnama!!.text = " : "+ snapshot.child("nama").value as String
                tvalamat!!.text = " : "+ snapshot.child("alamat").value as String
                tvemail!!.text = " : "+ snapshot.child("email").value as String
            }
            override fun onCancelled(databaseError: DatabaseError) {}
        })
        refkuy!!.child(uidkuy!!).child("lokasiku").orderByKey().limitToLast(1).addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snap: DataSnapshot) {
                val valku = snap!!.children
                valku.forEach{
                    val dataku = it.key.toString()
                    langiAppsen = snap.child(dataku).child("langiApp").value.toString()
                    latiAppsen = snap.child(dataku).child("latiApp").value.toString()
//                    btnlihat!!.text = langiAppsen+latiAppsen
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {}
        }

        )
        btnlihat!!.setOnClickListener {
//            val pindahan = Intent(this, MapsActivity::class.java)
//            startActivity(pindahan)
            pindahMap()
        }
    }
    fun pindahMap(){
        val pindahMapss = Intent(this, MapsActivity2::class.java)
        val datasen = Bundle()
        datasen.putString("latiApp", langiAppsen!!)
        datasen.putString("langiApp", latiAppsen!!)
        pindahMapss.putExtras(datasen)
        startActivity(pindahMapss)
    }
    fun inisialisasi(){

        tvnik = findViewById<View>(R.id.txt_nik) as TextView
        tvnama = findViewById<View>(R.id.txt_nama) as TextView
        tvalamat = findViewById<View>(R.id.txt_alamat) as TextView
        tvemail = findViewById<View>(R.id.txt_email) as TextView
        btnlihat = findViewById(R.id.btn_lihat) as Button
    }
    var listenerkuy: ValueEventListener= object : ValueEventListener{
        override fun onDataChange(snap: DataSnapshot) {
            val valku = snap!!.children
            valku.forEach{
                val dataku = it.key.toString()
                langiAppsen = snap.child(dataku).child("langiApp").value.toString()
                latiAppsen = snap.child(dataku).child("latiApp").value.toString()
            }
        }
        override fun onCancelled(databaseError: DatabaseError) {}
    }
}
