package com.example.questify

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.webkit.WebView
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide



class ResultActivity : AppCompatActivity() {

    private lateinit var scoreTextView: TextView
    lateinit var homeBtn: Button
    lateinit var resultimage: ImageView




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)
        resultimage = findViewById(R.id.resultimage)

        scoreTextView = findViewById(R.id.resultText)
        homeBtn= findViewById(R.id.homeBtn)
        var looseimages= arrayOf(R.drawable.loose1,R.drawable.loose2)

        val score = intent.getIntExtra("score", 0)
        val totalQuestions = intent.getIntExtra("totalQuestions", 0)
        val percentage = if (totalQuestions != 0) {
            (score * 100) / totalQuestions
        } else {
            0 // Or display a message indicating division by zero
        }

        if (score>= 5){
            Glide.with(this).load(R.drawable.congratulations).into(resultimage)
        }
        else{
            Glide.with(this).load(looseimages.random()).into(resultimage)
        }


        scoreTextView.text = getString(R.string.result_text, score, totalQuestions, percentage)

        // Add a button to return to the main menu or play again

        homeBtn.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}
