package com.armpatch.android.overlaydemoproject

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(){

    private lateinit var startButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        startButton = findViewById(R.id.start_service_button)
        startButton.setOnClickListener { startOverlayService() }

        getOverlayPermission(this)
    }


    fun startOverlayService() {
        if (Settings.canDrawOverlays(this)) {
            startService(Intent(this, OverlayService::class.java))
        } else {
            Toast.makeText(this,"Must Grant Permissions", Toast.LENGTH_SHORT).show()
        }
    }
}
