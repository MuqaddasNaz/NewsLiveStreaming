package com.example.Adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.newslivestreaming.R

class MainAdapter1(private val main1List: List<Pair<String, Int>>) :
    RecyclerView.Adapter<MainAdapter1.MainViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.main_row1, parent, false)
        return MainViewHolder(view)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val (title, imageResId) = main1List[position]
        holder.textView.text = title
        holder.imageView.setImageResource(imageResId)

        // Set click listener for the item
        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val youtubeLink = getYouTubeLinkForTitle(title)
            openYouTubeVideo(context, youtubeLink)
        }
    }

    override fun getItemCount(): Int {
        return main1List.size
    }

    private fun getYouTubeLinkForTitle(title: String): String {
        // Implement logic to map title to YouTube link here
        return when (title) {
            "Asaib Zadah|Discover the truth behind these perilous events with 7News" -> "https://www.youtube.com/playlist?list=PLuNY-hwz0BqQykdy5wX2TS3i2tTVNEjz4"
            "Sports Kay Sath|Dive into the world of sports with|7News" -> "https://www.youtube.com/playlist?list=PLuNY-hwz0BqTN97uanbF3fXTNZ3ptOF-F\n"
            "Damag Ki Batti|Shed light on crucial issues with|7News" -> "https://www.youtube.com/playlist?list=PLuNY-hwz0BqT598L2NL2559oMJGenjDCU\n"
            "Pakistan Kay Sath ,Table Talk, Barmala,Big7|Stay connected with Pakistan's journey with|7News" -> "https://www.youtube.com/playlist?list=PLuNY-hwz0BqTR4NYMg91yTSIqy-r_a22R"
            "Hisaab|Get insightful analysis with|7News" -> " https://www.youtube.com/playlist?list=PLuNY-hwz0BqQ7gmCYiPffPCNe3BytiqLm"
            "SIASAT|Stay informed with 7News analysis" -> " https://www.youtube.com/playlist?list=PLuNY-hwz0BqTdPIrH73Sm0rm67Z-9kHfG"
            // Add more cases for other titles
            else -> ""
        }
    }

    private fun openYouTubeVideo(context: Context, youtubeLink: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(youtubeLink))
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.setPackage("com.google.android.youtube")
        try {
            context.startActivity(intent)
        } catch (e: Exception) {
            // YouTube app is not installed, open in web browser
            context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(youtubeLink)))
        }
    }

    class MainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.findViewById(R.id.textView)
        val imageView: ImageView = itemView.findViewById(R.id.imageView)
    }
}
