package org.hyperskill.stopwatch


import android.app.AlertDialog
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    val list = mutableListOf(0, 0)
    val handler = Handler(Looper.getMainLooper())
    var isAlive = false
    var limit = 0
    var limitIsAlive = false


    lateinit var progressBar:ProgressBar
    lateinit var textView: TextView
    lateinit var settings: Button


    val task: Runnable = object : Runnable {
        override fun run() {
            if (limitIsAlive) {
                if (list[0] * 60 + list[1] > limit) {
                    textView.setTextColor(Color.RED)
                }
                else {
                    textView.setTextColor(Color.GRAY)
                }
            }
            else {
                textView.setTextColor(Color.GRAY)
            }

            textView.text = convert(list[0]) + ":" + convert(list[1])
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        textView = findViewById(R.id.textView)
        textView.setTextColor(Color.GRAY)

        progressBar = findViewById(R.id.progressBar)
        progressBar.visibility = View.GONE


        findViewById<Button>(R.id.startButton).setOnClickListener {
            if (!isAlive) {
                progressBar.visibility = View.VISIBLE
                list[1] += 1
                handler.postDelayed(task, 1000)
                handler.postDelayed(progressTask, 1000)
                isAlive = true
                settings.isEnabled = false
            }
        }

        findViewById<Button>(R.id.resetButton).setOnClickListener {
            if (isAlive) {
                progressBar.visibility = View.GONE
                list.fill(0)
                handler.removeCallbacks(progressTask)
                handler.removeCallbacks(task)
                textView.text = "00:00"
                textView.setTextColor(Color.GRAY)
                isAlive = false
                limitIsAlive = false
                settings.isEnabled = true
            }
        }

        val contentView = LayoutInflater.from(this).inflate(R.layout.dialog_main, null, false)
        val edit_text = contentView.findViewById<EditText>(R.id.upperLimitEditText)
        val alert = AlertDialog.Builder(this)
            .setTitle("Set upper limit in seconds")
            .setView(contentView)
            .setPositiveButton("OK") { _, _ ->
                if (edit_text.text.toString().isNotEmpty()) {
                    limit = edit_text.text.toString().toInt()
                    limitIsAlive = true
                }
                else {
                    limitIsAlive = false
                }
                edit_text.setText("")
            }
            .setNegativeButton("Cancel") { _, _ ->
                edit_text.setText("")
            }
            .create()


        settings = findViewById(R.id.settingsButton)

        settings.setOnClickListener {
                alert.show()
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


