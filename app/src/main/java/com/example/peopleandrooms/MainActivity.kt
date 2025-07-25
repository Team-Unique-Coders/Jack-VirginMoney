package com.example.peopleandrooms

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.peopleandrooms.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint


//add home
//add top nav that includes back end
//drop down menu ~ settings
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.people_menu -> {
                    navController.navigate(R.id.peopleFragment)
                    true
                }
                R.id.room_menu -> {
                    navController.navigate(R.id.roomFragment)
                    true
                }
                else -> false
            } }


        navController.addOnDestinationChangedListener { _, destination, _ ->

            binding.bottomNav.visibility = when (destination.id) {
                R.id.peopleFragment, R.id.roomFragment, R.id.homeFragment -> View.VISIBLE
                else -> View.GONE
            }
            val showBackButton = when (destination.id) {
                R.id.loginFragment -> false
                else -> true
            }
            supportActionBar?.setDisplayHomeAsUpEnabled(showBackButton)

            } }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                if (!navController.navigateUp()) {
                    finish()
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        } }
}