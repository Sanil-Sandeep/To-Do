package com.example.to_do

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.to_do.databinding.ActivityLaunchScreenBinding

class launchScreen: AppCompatActivity() {

    private lateinit var binding: ActivityLaunchScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLaunchScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set an OnClickListener to the root layout of the launch screen
        binding.root.setOnClickListener {
            // Launch MainActivity when the launch screen is clicked
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            // Optionally finish the launchScreen activity
            finish()
        }
    }
}
