package com.example.newslivestreaming

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import android.os.Bundle
import android.os.StrictMode
import android.os.StrictMode.VmPolicy
import android.view.View
import android.widget.HorizontalScrollView
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.Adapter.MainAdapter1
import com.example.Models.BreakingNews
import com.example.newslivestreaming.databinding.ActivityMainBinding
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ui.PlayerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.Timer
import kotlin.concurrent.timerTask

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mainAdapter1: MainAdapter1

    private var context: Context = this@MainActivity

    private val SCROLL_SPEED = 450
    private lateinit var imageViewLeft: ImageView
    private lateinit var imageViewRight: ImageView
    private lateinit var menuIcon: ImageButton
    private lateinit var expandableMenu: LinearLayout
    private lateinit var recyclerView: RecyclerView

    private lateinit var databaseReference: DatabaseReference

    var link = "http://116.58.87.13/0.flv"

    private lateinit var playerView: PlayerView
    private lateinit var simpleExoPlayer: SimpleExoPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val textView = findViewById<TextView>(R.id.programs)
        val isDarkMode = isDarkModeEnabled() // Function to check if dark mode is enabled
        if (isDarkMode) {
            textView.setTextColor(ContextCompat.getColor(this, R.color.white))
        } else {
            textView.setTextColor(ContextCompat.getColor(this, R.color.black))
        }


        val oldThreadPolicy = StrictMode.getThreadPolicy()
        StrictMode.setThreadPolicy(
            StrictMode.ThreadPolicy.Builder(oldThreadPolicy).permitDiskReads().build()
        )

        StrictMode.setThreadPolicy(StrictMode.ThreadPolicy.Builder(oldThreadPolicy).build())


        playerView = findViewById(R.id.player_view)

        // Create a SimpleExoPlayer instance
        simpleExoPlayer = SimpleExoPlayer.Builder(this).build()

        // Set the player to the player view
        playerView.player = simpleExoPlayer

        // Prepare the media source
        val videoUrl = "http://116.58.87.13/0.flv"
        val uri = Uri.parse(videoUrl)
        val mediaSource = MediaItem.fromUri(uri)


        // Prepare the player with the media source
        simpleExoPlayer.addMediaItem(mediaSource)

        // Start playback when ready
        simpleExoPlayer.playWhenReady = true
        val feedbackTextView: TextView = findViewById(R.id.tv_help)

        feedbackTextView.setOnClickListener {
            val intent = Intent(context, HelpActivity::class.java)
            startActivity(intent)
        }

        val builder = VmPolicy.Builder()
        StrictMode.setVmPolicy(builder.build())

        databaseReference = FirebaseDatabase.getInstance().reference.child("breaking_news")

        getBreakingNews()

        Functions.setTransparentStatusBar(context)
        menuIcon = findViewById(R.id.menu_icon)
        imageViewLeft = findViewById(R.id.imageViewLeft)
        imageViewRight = findViewById(R.id.imageViewRight)
        expandableMenu = findViewById(R.id.expandable_menu)
        recyclerView = findViewById(R.id.rv_main1)

        playerView = findViewById(R.id.player_view)
        simpleExoPlayer = SimpleExoPlayer.Builder(this).build()
        playerView.player = simpleExoPlayer

        menuIcon.setOnClickListener {
            toggleSideMenu()
        }

        setupRecyclerViews()
        setupTextViewClickListeners()

        imageViewLeft.setOnClickListener {
            recyclerView.smoothScrollBy(-SCROLL_SPEED, 0)
        }

        imageViewRight.setOnClickListener {
            recyclerView.smoothScrollBy(SCROLL_SPEED, 0)
        }


    }

    private fun isDarkModeEnabled(): Boolean {
        // Get the current system configuration
        val currentNightMode = this.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK

        // Check if night mode is enabled
        return currentNightMode == Configuration.UI_MODE_NIGHT_YES
    }

    private fun setupTextViewClickListeners() {
        val asaibZadahTextView: TextView = findViewById(R.id.tv_asaib_program)
        val sportsKaySathTextView: TextView = findViewById(R.id.tv_sports_sath)
        val damagKiBattiTextView: TextView = findViewById(R.id.tv_damag_batti)
        val pakistanKaySathTextView: TextView = findViewById(R.id.tv_pakistan_program)
        val hisaabProgramTextView: TextView = findViewById(R.id.tv_hisaab_program)
        val siasatProgramTextView: TextView = findViewById(R.id.tv_siasat_program)


        asaibZadahTextView.setOnClickListener {
            openYouTubeVideo("https://www.youtube.com/playlist?list=PLuNY-hwz0BqQykdy5wX2TS3i2tTVNEjz4")
        }

        sportsKaySathTextView.setOnClickListener {
            openYouTubeVideo("https://www.youtube.com/playlist?list=PLuNY-hwz0BqTN97uanbF3fXTNZ3ptOF-F")
        }

        damagKiBattiTextView.setOnClickListener {
            openYouTubeVideo("https://www.youtube.com/playlist?list=PLuNY-hwz0BqT598L2NL2559oMJGenjDCU")
        }

        pakistanKaySathTextView.setOnClickListener {
            openYouTubeVideo("https://www.youtube.com/playlist?list=PLuNY-hwz0BqTR4NYMg91yTSIqy-r_a22R")
        }

        hisaabProgramTextView.setOnClickListener {
            openYouTubeVideo("https://www.youtube.com/playlist?list=PLuNY-hwz0BqQ7gmCYiPffPCNe3BytiqLm")
        }

        siasatProgramTextView.setOnClickListener {
            openYouTubeVideo("https://www.youtube.com/playlist?list=PLuNY-hwz0BqTdPIrH73Sm0rm67Z-9kHfG")
        }
    }

    private fun openYouTubeVideo(youtubeLink: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(youtubeLink))
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.setPackage("com.google.android.youtube")
        try {
            startActivity(intent)
        } catch (e: Exception) {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(youtubeLink)))
        }
    }

    private fun toggleSideMenu() {
        if (expandableMenu.visibility == View.VISIBLE) {
            expandableMenu.visibility = View.GONE
        } else {
            expandableMenu.visibility = View.VISIBLE
        }
    }

    private fun getBreakingNews(){

        databaseReference.child("news").addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val breakingNews = snapshot.getValue(BreakingNews::class.java)

                setupBreakingNews(breakingNews)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })


    }
    private fun setupBreakingNews(breakingNews: BreakingNews?) {
//        val breakingNewsText =
//            "      نیوز7 سچ کے ساتھ | بریکنگ نیوز: سپریم کورٹ نے انتخابات کا لعدم قرار دینے کی درخواست خارج کردی۔             نیوز7 سچ کے ساتھ | بریکنگ نیوز: سپریم کورٹ نے انتخابات کا لعدم قرار دینے کی درخواست خارج کردی۔                نیوز7 سچ کے ساتھ | بریکنگ نیوز: سپریم کورٹ نے انتخابات کا لعدم قرار دینے کی درخواست خارج کردی۔"

        val textViewBreakingNews: TextView = findViewById(R.id.textViewBreakingNews)
        textViewBreakingNews.text = breakingNews?.description

        val horizontalScrollView: HorizontalScrollView = findViewById(R.id.horizontalScrollView)
        val timer = Timer()
        timer.scheduleAtFixedRate(timerTask {
            runOnUiThread {
                if (horizontalScrollView.scrollX >= (textViewBreakingNews.width - horizontalScrollView.width)) {
                    horizontalScrollView.scrollTo(0, 0)
                } else {
                    horizontalScrollView.smoothScrollBy(2, 0)
                }
            }
        }, 0, SCROLL_INTERVAL)
    }


    private fun setupRecyclerViews() {
        val main1List = listOf(
            Pair("Asaib Zadah|Discover the truth behind these perilous events with 7News", R.drawable.image_ary11),
            Pair("Sports Kay Sath|Dive into the world of sports with|7News", R.drawable.news7_3),
            Pair("Damag Ki Batti|Shed light on crucial issues with|7News", R.drawable.gb_bti_img),
            Pair("Pakistan Kay Sath ,Table Talk, Barmala,Big7|Stay connected with Pakistan's journey with|7News", R.drawable.pks_img),
            Pair("Hisaab|Get insightful analysis with|7News", R.drawable.news7_2),
            Pair("Siasat|Stay informed with 7News analysis", R.drawable.news7_1),
        )

        val mainAdapter1 = MainAdapter1(main1List)

        val recyclerViewMain1: RecyclerView = findViewById(R.id.rv_main1)
        recyclerViewMain1.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerViewMain1.adapter = mainAdapter1
    }

    companion object {
        const val SCROLL_INTERVAL = 30L
    }
}
