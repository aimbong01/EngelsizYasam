package com.engelsizyasam.presentation.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.engelsizyasam.R
import com.engelsizyasam.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.myNavHostFragment) as NavHostFragment
        val navController = navHostFragment.navController

        setupBottomNavMenu(navController)

    }

    private fun setupBottomNavMenu(navController: NavController) {
        val bottomNav = binding.bottomBar
        bottomNav.setupWithNavController(navController)
    }

    override fun onBackPressed() {
        binding.bottomBar.translationY = 0f
        super.onBackPressed()
    }

}