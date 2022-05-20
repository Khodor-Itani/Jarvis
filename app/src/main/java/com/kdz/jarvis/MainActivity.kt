package com.kdz.jarvis

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.kdz.jarvis.extensions.applyTaskDescription
import com.kdz.jarvis.network.result.NetworkResult
import com.kdz.jarvis.network.services.MarvelService
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