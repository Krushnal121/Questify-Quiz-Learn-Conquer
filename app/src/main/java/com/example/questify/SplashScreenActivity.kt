package com.example.questify

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView


class SpalshScreenActivity : AppCompatActivity()
{

    lateinit var splash: ImageView
    lateinit var splashText1: TextView
    lateinit var splashText2: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );
        setContentView(R.layout.activity_splash_screen)

        splash = findViewById(R.id.splashImageview)
        splashText1 = findViewById(R.id.splashText01)
        splashText2 = findViewById(R.id.splashText02)
        splash.alpha=0f
        splashText1.alpha=0f
        splashText2.alpha=0f
        splashText1.animate().setDuration(1500).alpha(1f)
        splashText2.animate().setDuration(1500).alpha(1f)
        splash.animate().setDuration(1500).alpha(1f).withEndAction {
            val i = Intent(this, MainActivity::class.java)
            startActivity(i)
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            finish()
        }

    }
}