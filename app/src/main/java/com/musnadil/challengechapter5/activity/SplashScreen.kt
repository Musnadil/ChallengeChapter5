package com.musnadil.challengechapter5.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.core.content.ContextCompat
import com.musnadil.challengechapter5.R
import com.musnadil.challengechapter5.databinding.ActivitySplashScreenBinding

class SplashScreen : AppCompatActivity() {
    private lateinit var binding: ActivitySplashScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        this.window.statusBarColor = ContextCompat.getColor(this, R.color.white)
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this@SplashScreen,MainActivity::class.java))
            finish()
        }, 5000)
    }
}