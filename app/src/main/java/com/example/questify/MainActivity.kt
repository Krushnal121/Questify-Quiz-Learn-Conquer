package com.example.questify

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.widget.Button
import android.widget.ImageView
import com.bumptech.glide.Glide


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        val bannerImage = findViewById<ImageView>(R.id.bannerImage)
        val bannerimages= arrayOf(R.drawable.homebanner1,R.drawable.homebanner2,R.drawable.homebanner3,R.drawable.homebanner4)
        val radomQuiz= arrayOf("science","history","genK","tech")



        val scienceButton = findViewById<Button>(R.id.scienceBtn)
        val historyButton = findViewById<Button>(R.id.historyBtn)
        val genKButton = findViewById<Button>(R.id.genKBtn)
        val techButton = findViewById<Button>(R.id.techBtn)
        val randomButton = findViewById<Button>(R.id.randomBtn)
        val infoButton = findViewById<Button>(R.id.infoBtn)

        scienceButton.setOnClickListener {
            startQuizActivity("science")
        }

        historyButton.setOnClickListener {
            startQuizActivity("history")
        }

        genKButton.setOnClickListener {
            startQuizActivity("genK")
        }

        techButton.setOnClickListener {
            startQuizActivity("tech")
        }

        randomButton.setOnClickListener {
            startQuizActivity(radomQuiz.random())
        }

        Glide.with(this).load(bannerimages.random()).into(bannerImage)

        infoButton.setOnClickListener {
            startActivity(Intent(this, ProjectInfo::class.java))
        }
    }

    private fun startQuizActivity(topic: String) {
        val intent = Intent(this, QuizActivity::class.java)
        intent.putExtra("topic", topic)
        startActivity(intent)
    }
}