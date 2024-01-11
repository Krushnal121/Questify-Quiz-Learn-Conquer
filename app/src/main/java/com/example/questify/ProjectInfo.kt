package com.example.questify

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView

class ProjectInfo : AppCompatActivity() {

    private lateinit var webBtn: Button
    private lateinit var instaBtn: Button
    private lateinit var linkedinBtn: Button
    private lateinit var githubBtn: Button
    private lateinit var homeBtn: Button



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_project_info)

        webBtn =findViewById(R.id.webBtn)
        instaBtn = findViewById(R.id.instagramBtn)
        linkedinBtn = findViewById(R.id.linkedinBtn)
        githubBtn = findViewById(R.id.githubBtn)
        homeBtn= findViewById(R.id.homeBtnInfoScreen)


        webBtn.setOnClickListener {
            val websiteUrl = "https://bento.me/krushnalpatil"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(websiteUrl))
            startActivity(intent)
        }

        instaBtn.setOnClickListener {
            val websiteUrl =
                "https://www.instagram.com/krushnal_patil_111"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(websiteUrl))
            startActivity(intent)
        }

        linkedinBtn.setOnClickListener {
            val websiteUrl =
                "https://www.linkedin.com/in/krushnal-jagannath-patil/"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(websiteUrl))
            startActivity(intent)
        }

        githubBtn.setOnClickListener {
            val websiteUrl =
                "https://github.com/Krushnal121"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(websiteUrl))
            startActivity(intent)
        }

        homeBtn.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

    }
}