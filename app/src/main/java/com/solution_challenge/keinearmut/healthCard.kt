package com.solution_challenge.keinearmut

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity

class healthCard: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.health_card)

        val imageView: ImageView = findViewById(R.id.imageView2)
        imageView.setOnClickListener {
            openWebsite()
        }
        val imageView1: ImageView = findViewById(R.id.imageView3)
        imageView1.setOnClickListener {
            openwebsite1()
        }
        val imageView2: ImageView = findViewById(R.id.imageView4)
        imageView2.setOnClickListener {
            openwebsite2()
        }
    }
    fun openWebsite() {
        val url = "https://abdm.gov.in/"
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(url)
        startActivity(intent)
    }
    fun openwebsite1() {
        val url = "https://www.india.gov.in/spotlight/rashtriya-swasthya-bima-yojana"
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(url)
        startActivity(intent)
    }
    fun openwebsite2() {
        val url = "https://cghs.gov.in/CghsGovIn/faces/ViewPage.xhtml"
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(url)
        startActivity(intent)
    }
}