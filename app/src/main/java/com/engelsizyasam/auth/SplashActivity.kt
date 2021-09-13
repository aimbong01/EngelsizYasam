package com.engelsizyasam.auth

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import androidx.appcompat.app.AppCompatActivity
import com.engelsizyasam.databinding.ActivitySplashBinding
import com.engelsizyasam.ui.MainActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SplashActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth
        val currentUser = auth.currentUser

        val login = Intent(this, LoginActivity::class.java)
        val main = Intent(this, MainActivity::class.java)

        var i = 0
        val timer = object : CountDownTimer(2000, 1000) {

            override fun onTick(millisUntilFinished: Long) {
                binding.splashProgress.progress = i * 100 / (2000 / 1000)
                i++
            }

            override fun onFinish() {
                i++
                binding.splashProgress.progress = 100
                if (currentUser != null) {
                    startActivity(main)
                    finish()
                } else {
                    startActivity(login)
                    finish()
                }
            }
        }
        timer.start()
    }
}