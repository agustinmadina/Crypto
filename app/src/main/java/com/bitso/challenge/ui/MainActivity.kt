package com.bitso.sample.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.navigateUp
import com.bitso.sample.R
import com.bitso.sample.databinding.ActivityMainBinding
import com.bitso.sample.extensions.viewBinding

class MainActivity : AppCompatActivity() {
    private val binding by viewBinding(ActivityMainBinding::inflate)
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupActionBar()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed() // to trigger OnBackPressedCallbacks in your fragments
        return navigateUp()
    }

    private fun setupActionBar() {
        setSupportActionBar(binding.toolbar)
        navController =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment)!!.findNavController()
        appBarConfiguration = AppBarConfiguration(navController.graph)
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration)
    }

    private fun navigateUp() =
        navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
}
