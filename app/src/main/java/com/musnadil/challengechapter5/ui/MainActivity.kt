package com.musnadil.challengechapter5.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.musnadil.challengechapter5.R
import com.musnadil.challengechapter5.ui.theme.lightStatusBar
import com.musnadil.challengechapter5.ui.theme.setFullScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setFullScreen(window)
        lightStatusBar(window)
    }
}