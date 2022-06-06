package com.musnadil.challengechapter5.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.*
import com.musnadil.challengechapter5.R
import com.musnadil.challengechapter5.ui.theme.ChallengeChapter5Theme
import kotlinx.coroutines.delay

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ChallengeChapter5Theme {
                Surface(color = MaterialTheme.colors.background) {
                    Column {
                        SplashScreen()
                    }
                }
            }
        }
    }

    @Composable
    fun SplashScreen() {
        LaunchedEffect(key1 = true) {
            delay(5000)
            startActivity(Intent(this@SplashScreenActivity, MainActivity::class.java))
        }
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .padding(all = 100.dp)
                .fillMaxSize()
        ) {
            loader()
        }
    }

    @Composable
    private fun loader() {
        val compositionResult: LottieCompositionResult = rememberLottieComposition(
            spec = LottieCompositionSpec.RawRes(R.raw.e_news)
        )
        val progress by animateLottieCompositionAsState(
            compositionResult.value,
            isPlaying = true,
            iterations = 1,
            speed = 1.0f
        )
        LottieAnimation(
            compositionResult.value,
            progress
        )
    }

    @Preview(showBackground = true, showSystemUi = true)
    @Composable
    fun DefaultPreview() {
        ChallengeChapter5Theme {
            Column {
                SplashScreen()
            }
        }
    }
}