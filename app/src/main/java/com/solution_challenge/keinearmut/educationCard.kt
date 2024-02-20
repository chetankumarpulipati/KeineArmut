package com.solution_challenge.keinearmut

import android.os.Bundle
import android.webkit.WebView
import androidx.appcompat.app.AppCompatActivity

class educationCard: AppCompatActivity() {
    private lateinit var webView: WebView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.education_card)

        webView = findViewById(R.id.education_card_webview)
        val video = "<iframe width=\"300\" height=\"250\" src=\"https://www.youtube.com/embed/hq3yfQnllfQ?si=BAYYjzy-cm4SDARC\" title=\"YouTube video player\" frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share\" allowfullscreen></iframe>"
        webView.loadData(video, "text/html", "utf-8")   // Load the video into the webview
        webView.settings.javaScriptEnabled = true
    }
}