package com.kdz.jarvis

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.kdz.jarvis.extensions.applyTaskDescription

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        applyTaskDescription()
    }
}