package com.example.firebasehp3

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.TextView
import com.google.firebase.database.*

class AdapterApp(val mCtx: Context, val layoutId: Int, val dataList: List<MyData>): ArrayAdapter<MyData>(mCtx, layoutId, dataList) {

    private var refkuy: DatabaseReference? = null
    private var langiAppsen: String? = "0.0"
    private var latiAppsen: String? = "0.0"
    private var add1: Double? = 0.0
    private var add2: Double? = 0.0

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater: LayoutInflater = LayoutInflater.from(mCtx)
        val view: View = layoutInflater.inflate(layoutId, null)
        val txtName = view.findViewById<TextView>(R.id.txt_nama)
        val txtAlamat = view.findViewById<TextView>(R.id.txt_alamat)
        val btnlihat = view.findViewById<Button>(R.id.btn_lihat)
        val mydata = dataList[position]
        txtName.text = mydata.nama
        txtAlamat.text = mydata.alamat

//        uidku = mydata.uidku

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
        btnlihat.setOnClickListener {


//            val pindahMap = Intent(mCtx, Main2Activity::class.java)
            val pindahkan = Intent(view.context, Main2Activity::class.java)
            val datasen = Bundle()
//            datasen.putString("uidku", mydata.uidku)
//            datasen.putString("langiApp", langiAppsen)
//            datasen.putString("latiApp", latiAppsen)
//            pindahMap.putExtras(datasen)
//            btnlihat.context.startActivity(pindahMap)
            view.context.startActivity(pindahkan)

        }
        return view
    }

}