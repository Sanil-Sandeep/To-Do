package com.example.to_do

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.to_do.databinding.ActivityUpdateBinding

class UpdateActivity : AppCompatActivity() {

    private lateinit var binding:ActivityUpdateBinding
    private lateinit var db:TaskDatabaseHelper
    private   var taskId:Int=-1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)

       db=TaskDatabaseHelper(this)

        taskId=intent.getIntExtra("task_id",-1)
        if(taskId==-1){
            finish()
            return
        }

        val task = db.getTaskByID(taskId)
        binding.updateTitleEditText.setText(task.title)
        binding.updateContentEditText.setText(task.content)
        binding.updatedeadlineEditText.setText(task.priority) // Assuming priority is the correct property name
        binding.updatetvtextTime.setText(task.dateTime)


        binding.updateSaveButton.setOnClickListener {
            val newTitle=binding.updateTitleEditText.text.toString()
            val newContent=binding.updateContentEditText.text.toString()
            val newPriority=binding.updatedeadlineEditText.text.toString()
            val newdateTime=binding.updatetvtextTime.text.toString()
            val updatedTask=Task(taskId,newTitle,newContent,newPriority,newdateTime)
            db.updateTask(updatedTask)
            finish()
            Toast.makeText(this,"Changes Saved",Toast.LENGTH_SHORT).show()

        }


    }
}