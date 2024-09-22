package com.example.to_do

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.to_do.databinding.ActivityMain2Binding
import com.example.to_do.databinding.ActivityMainBinding
import java.util.Date
import java.util.Timer
import java.util.TimerTask

class MainActivity2 : AppCompatActivity() {

    lateinit var binding: ActivityMain2Binding
    lateinit var dataHelper: DataHelper
    private val timer = Timer()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain2Binding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        dataHelper = DataHelper((applicationContext))

        binding.startButton.setOnClickListener{statStopAction()}
        binding.resetButton.setOnClickListener{resetStopAction()}

        if (dataHelper.timerCounting()){
            startTimer()
        }
        else{
            stopTimer()
            if (dataHelper.startTime() != null && dataHelper.stopTime() != null){
                val time = Date().time - calcReStartTime().time
                binding.timeTV.text = timeStringFromLong(time)
            }
        }

        timer.scheduleAtFixedRate(TimeTask(), 0, 500)



        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private inner class TimeTask : TimerTask() {
        override fun run() {
            if (dataHelper.timerCounting()) {
                val time = Date().time - dataHelper.startTime()!!.time
                runOnUiThread {
                    binding.timeTV.text = timeStringFromLong(time)  // This updates the UI safely on the main thread
                }
            }
        }
    }


    private fun resetStopAction() {
        dataHelper.setStoptime(null)
        dataHelper.setStartTime(null)
        stopTimer()
        binding.timeTV.text = timeStringFromLong(0)
    }

    private fun stopTimer() {
        dataHelper.setTimerCounting(false)
        binding.startButton.text = getString(R.string.start)
    }

    private fun startTimer() {
        dataHelper.setTimerCounting(true)
        binding.startButton.text = getString(R.string.stop)
    }

    private fun statStopAction() {
        if(dataHelper.timerCounting()){
            dataHelper.setStoptime(Date())
            stopTimer()
        }
        else{
            if (dataHelper.stopTime() != null){
                dataHelper.setStartTime(calcReStartTime())
                dataHelper.setStoptime(null)
            }
            else{
                dataHelper.setStartTime(Date())
            }
            startTimer()
        }
    }

    private fun calcReStartTime(): Date {
        val diff = (dataHelper.startTime()?.time ?: 0L) - (dataHelper.stopTime()?.time ?: 0L)
        return Date(System.currentTimeMillis() + diff)
    }

    private fun timeStringFromLong(ms: Long): String? {
        val seconds = (ms / 1000) % 60
        val minutes = (ms / (1000 * 60) % 60)
        val hours = (ms / (1000 * 60 * 60)) % 24

        return makeTimeString(hours, minutes, seconds)
    }

    private fun makeTimeString(hours: Long, minutes: Long, seconds: Long): String? {

        return String.format("%02d:%02d:%02d", hours, minutes, seconds)

    }
}