package com.kdz.jarvis.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.kdz.jarvis.R
import com.kdz.jarvis.extensions.applyTaskDescription
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlinx.coroutines.runBlocking

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        applyTaskDescription()
    }
}