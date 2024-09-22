package com.example.to_do

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.to_do.databinding.ActivityAddTaskBinding
import java.time.Month
import java.time.Year
import java.util.Calendar

class AddTask : AppCompatActivity(), DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    var day=0
    var month=0
    var year=0
    var hour=0
    var minute=0

    var saveDay=0
    var saveMonth=0
    var saveYear=0
    var saveHour=0
    var saveMinute=0

    private lateinit var binding:ActivityAddTaskBinding
    private lateinit var db:TaskDatabaseHelper


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityAddTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        pickDate()

        db= TaskDatabaseHelper(this)

        binding.saveButton.setOnClickListener{
            val title = binding.titleEditText.text.toString()
            val content = binding.contentEditText.text.toString()
            val priority = binding.deadlineEditText.text.toString()
             val dateTime = "$saveDay-$saveMonth-$saveYear $saveHour:$saveMinute"
            val task = Task(0, title, content, priority,dateTime) // Include dateTime in the constructor
            db.insertTask(task)
            finish()
            Toast.makeText(this, "Task saved", Toast.LENGTH_SHORT).show()
        }

        }
    private fun getDateTimeCalendar(){
        val cal: Calendar = Calendar.getInstance()
        day=cal.get(Calendar.DAY_OF_MONTH)
        month=cal.get(Calendar.MONTH)
        year=cal.get(Calendar.YEAR)
        hour=cal.get(Calendar.HOUR)
        minute=cal.get(Calendar.MINUTE)

    }

    private fun pickDate(){
        binding.btnTimePicker.setOnClickListener{
            getDateTimeCalendar()

            DatePickerDialog(this,this,year,month,day).show()
        }
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        saveDay = dayOfMonth
        saveMonth = month+1
        saveYear = year

        getDateTimeCalendar()
        TimePickerDialog(this, this, hour, minute, true).show()
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        saveHour=hourOfDay
        saveMinute=minute

        binding.tvTextTime.text="$saveDay-$saveMonth-$saveYear\n Hour:$saveHour Minute:$saveMinute"
    }
}
