package com.example.newslivestreaming

import android.app.Service
import android.content.Intent
import android.os.Handler
import android.os.IBinder

class AdService : Service() {
    private val mHandler = Handler()
    private lateinit var mRunnable: Runnable
    private val mBreakingNews = listOf(
        "Breaking News 1",
        "Breaking News 2",
        "Breaking News 3"
    )
    private var mIndex = 0

    override fun onCreate() {
        super.onCreate()
        mRunnable = object : Runnable {
            override fun run() {
                val breakingNews = mBreakingNews[mIndex]
                broadcastBreakingNews(breakingNews)
                mIndex = (mIndex + 1) % mBreakingNews.size
                mHandler.postDelayed(this, BREAKING_NEWS_INTERVAL)
            }
        }
        mHandler.post(mRunnable)
    }

    override fun onDestroy() {
        super.onDestroy()
        mHandler.removeCallbacks(mRunnable)
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    private fun broadcastBreakingNews(news: String) {
        val intent = Intent(BROADCAST_BREAKING_NEWS)
        intent.putExtra(EXTRA_BREAKING_NEWS, news)
        sendBroadcast(intent)
    }

    companion object {
        const val BROADCAST_BREAKING_NEWS = "com.example.newslivestreaming.BROADCAST_BREAKING_NEWS"
        const val EXTRA_BREAKING_NEWS = "extra_breaking_news"
        private const val BREAKING_NEWS_INTERVAL: Long = 5000
    }
}

