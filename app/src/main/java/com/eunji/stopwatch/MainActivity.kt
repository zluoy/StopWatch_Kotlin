package com.eunji.stopwatch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.concurrent.timer

class MainActivity : AppCompatActivity() {
    private var time = 0
    private var timerTask: Timer? = null
    private var isRunning = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fab.setOnClickListener {
            isRunning = !isRunning

            if(isRunning) {
                start()
            } else {
                pause()
            }
        }

        lapButton.setOnClickListener {
            recordLapTime()
        }

        resetFab.setOnClickListener {
            reset()
        }
    }


    private fun start(){
        fab.setImageResource(R.drawable.ic_baseline_pause_24)

        timerTask = timer(period = 10) {
            time++

            val sec = time / 100
            val milli = time % 100

            runOnUiThread {
                secTime.text = "$sec"
                millisecTime.text = "$milli"
            }
        }
    }

    private fun pause() {
        fab.setImageResource(R.drawable.ic_baseline_play_arrow_24)
        timerTask?.cancel()

    }

    private var lap = 1
    private fun recordLapTime() {
        val lapTime = this.time
        val textView = TextView(this)

        textView.text = "$lap LAP : ${lapTime/100}.${lapTime%100}"

        linearlayout.addView(textView, 0)
        lap++
    }

    private fun reset() {
        timerTask?.cancel()

        time = 0
        isRunning = false

        fab.setImageResource(R.drawable.ic_baseline_play_arrow_24)
        secTime.text = "0"
        millisecTime.text = "00"

        linearlayout.removeAllViews()
        lap = 1

        Toast.makeText(this,"RESET !", Toast.LENGTH_SHORT).show()
    }
}