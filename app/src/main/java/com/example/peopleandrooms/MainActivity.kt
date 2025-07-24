package com.example.peopleandrooms

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.peopleandrooms.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint


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

        binding.bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.people_menu -> {
                    navController.navigate(R.id.peopleFragment)
                    true }
                R.id.room_menu -> {
                    navController.navigate(R.id.roomFragment)
                    true } else -> false } }
        //changing visibility of the bottomNav
        navController.addOnDestinationChangedListener{_, destination, _ ->
            when (destination.id){
                R.id.peopleFragment, R.id.roomFragment -> binding.bottomNav.visibility = View.VISIBLE
                else -> binding.bottomNav.visibility = View.GONE
            } } }


}
