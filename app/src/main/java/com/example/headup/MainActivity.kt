package com.example.headup

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.core.view.isVisible
import com.example.headup.CelebrityGame
import com.example.headup.R

class MainActivity : AppCompatActivity() {
    private lateinit var startButton: Button
    private lateinit var timer: TextView
    private  lateinit var welcomeTextView : TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        timer = findViewById(R.id.tvTimer)
        welcomeTextView = findViewById(R.id.welcome)

        startButton = findViewById(R.id.startButton)
        startTimerWelcome()

        startButton.setOnClickListener {

            timer.visibility = View.VISIBLE
            welcomeTextView.visibility = View.INVISIBLE
            startButton.visibility = View.INVISIBLE

            startTimer()
        }

    }
    private fun startTimer() {
        object : CountDownTimer(4000, 1000) {

            override fun onTick(millisUntilFinished: Long) {
                timer.text = "${millisUntilFinished / 1000}"
            }

            override fun onFinish() {
                timer.text = "Go"
                timer.setTextColor(Color.GREEN)
                startActivity(Intent(this@MainActivity, CelebrityGame::class.java))
            }
        }.start()
    }
    private fun startTimerWelcome() {
        object : CountDownTimer(4000, 1000) {

            override fun onTick(millisUntilFinished: Long) {
            }

            override fun onFinish() {
               startButton.visibility = View.VISIBLE
            }
        }.start()
    }
}