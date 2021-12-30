package org.hyperskill.stopwatch

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.graphics.drawable.DrawableWrapper
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    val list = mutableListOf(0, 0)
    val handler = Handler(Looper.getMainLooper())
    var isAlive = false


    lateinit var progressBar:ProgressBar
    lateinit var textView: TextView


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
    var changeColor = false

    val progressTask: Runnable = object : Runnable {
        override fun run() {
            if (changeColor) {
                progressBar.indeterminateTintList = ColorStateList.valueOf(Color.parseColor("#4169E1"))
            }
            else {
                progressBar.indeterminateTintList = ColorStateList.valueOf(Color.parseColor("#F44336"));
            }
            changeColor = !changeColor
            handler.postDelayed(this, 1000)
        }

    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        textView = findViewById(R.id.textView)
        progressBar = findViewById(R.id.progressBar)
        progressBar.visibility = View.GONE


        findViewById<Button>(R.id.startButton).setOnClickListener {
            if (!isAlive) {
                progressBar.visibility = View.VISIBLE
                list[1] += 1
                handler.postDelayed(task, 1000)
                handler.postDelayed(progressTask, 1000)
                isAlive = true
            }
        }

        findViewById<Button>(R.id.resetButton).setOnClickListener {
            if (isAlive) {
                progressBar.visibility = View.GONE
                list.fill(0)
                handler.removeCallbacks(progressTask)
                handler.removeCallbacks(task)
                textView.text = "00:00"
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


