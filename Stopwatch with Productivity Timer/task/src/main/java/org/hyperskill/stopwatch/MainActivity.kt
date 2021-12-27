package org.hyperskill.stopwatch

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    val list = mutableListOf(0, 0)
    val handler = Handler(Looper.getMainLooper())
    var isAlive = false

    var textView: TextView? = null
    val task: Runnable = object : Runnable {
        override fun run() {
            textView?.text = convert(list[0]) + ":" + convert(list[1])
            list[1]++
            if (list[1] == 60) {
                list[1] = 0;
                list[0] += 1;
            }
            handler.postDelayed(this, 1000)
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        textView = findViewById(R.id.textView)

        findViewById<Button>(R.id.startButton).setOnClickListener {
            if (!isAlive) {
                list[1] += 1
                handler.postDelayed(task, 1000)
                isAlive = true
            }
        }

        findViewById<Button>(R.id.resetButton).setOnClickListener {
            if (isAlive) {
                list.fill(0)
                handler.removeCallbacks(task)
                textView?.text = "00:00"
                isAlive = false
            }
        }
    }



    fun convert(value: Int):String {
        return if (value < 10) {
            "0$value"
        }
        else {
            value.toString()
        }
    }
}


