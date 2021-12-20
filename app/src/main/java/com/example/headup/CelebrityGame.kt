package com.example.headup

import android.content.Intent
import android.content.res.Configuration
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CelebrityGame : AppCompatActivity() {
    private var celebrityCount = 0
    private  lateinit var celebrity: Celebrity
    private  lateinit var landscapeLayout: LinearLayout
    private  lateinit var portraitLayout: ConstraintLayout
    private  lateinit var timerTextView: TextView
    lateinit var celebrityTextViews: List<TextView>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_celebrity_game)

        landscapeLayout = findViewById(R.id.celeb_info_layout)
        portraitLayout = findViewById(R.id.portrait_layout)
        timerTextView = findViewById(R.id.tvTimer)

        getAllCelebrity()
        startTimer()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            updateCelebViews()
            portraitLayout.visibility = INVISIBLE
            landscapeLayout.visibility = VISIBLE
        }
        else {
            ++celebrityCount
            landscapeLayout.visibility = INVISIBLE
            portraitLayout.visibility = VISIBLE
        }
    }

    private fun getAllCelebrity() {
        val apiInterface = APIClient().getClient()?.create(APIInterface::class.java)
        apiInterface!!.getAllCelebrity()?.enqueue(object: Callback<Celebrity?> {

            override fun onFailure(call: Call<Celebrity?>, t: Throwable) {
                Toast.makeText(this@CelebrityGame, t.message, Toast.LENGTH_SHORT).show()
                call.cancel()
            }

            override fun onResponse(call: Call<Celebrity?>, response: Response<Celebrity?>) {
                celebrity = response.body()!!

            }
        })
    }

    private fun updateCelebViews() {
        celebrityTextViews = listOf (
            findViewById(R.id.tvCelebName),
            findViewById(R.id.tvCelebTaboo1),
            findViewById(R.id.tvCelebTaboo2),
            findViewById(R.id.tvCelebTaboo3)
        )
        val currentCelebrity = celebrity[celebrityCount]
        celebrityTextViews[0].text = currentCelebrity.name
        celebrityTextViews[1].text = currentCelebrity.taboo1
        celebrityTextViews[2].text = currentCelebrity.taboo2
        celebrityTextViews[3].text = currentCelebrity.taboo3
    }

    private fun startTimer() {
        object : CountDownTimer(60000, 1000) {

            override fun onTick(millisUntilFinished: Long) {
                timerTextView.text = "Time: ${millisUntilFinished / 1000}"
            }

            override fun onFinish() {
                timerTextView.text = "Time: 0"
                timerTextView.setTextColor(Color.RED)
                startActivity(Intent(this@CelebrityGame, MainActivity::class.java))
            }
        }.start()
    }

}