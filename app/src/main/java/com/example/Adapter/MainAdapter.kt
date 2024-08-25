package com.example.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.newslivestreaming.R

class MainAdapter(mainList: Any?) : RecyclerView.Adapter<MainAdapter.MainViewHolder>() {

    private val data = arrayOf("Breaking news ECP rejects Rawalpindi commissioner's rigging allegations", "Breaking news ECP rejects Rawalpindi commissioner's rigging allegations", "Breaking news ECP rejects Rawalpindi commissioner's rigging allegations", "Breaking news ECP rejects Rawalpindi commissioner's rigging allegations", "Breaking news ECP rejects Rawalpindi commissioner's rigging allegations")

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.main_row, parent, false)
        return MainViewHolder(view)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.textView.text = data[position]
    }

    override fun getItemCount(): Int {
        return data.size
    }

    class MainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.findViewById(R.id.textView)
    }
}
