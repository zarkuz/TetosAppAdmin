package com.example.firebasehp3

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MyAdapter(private val listData: ArrayList<ListDataku>): RecyclerView.Adapter<MyAdapter.ListViewHolder>() {
    inner class ListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var tvNama: TextView = itemView.findViewById(R.id.txt_nama)
        var tvAlamat: TextView = itemView.findViewById(R.id.txt_alamat)
        var btnLihat: Button = itemView.findViewById(R.id.btn_lihat)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.activity_mydata, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val dataku = listData[position]
        holder.tvNama.text = dataku.nama
        holder.tvAlamat.text = dataku.alamat
        holder.btnLihat.setOnClickListener{
            val intent = Intent(holder.itemView.context, MapsActivity::class.java)
            val bundle = Bundle()
            bundle.putString("uid", dataku.uidku)
            intent.putExtras(bundle)
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return listData.size
    }
}