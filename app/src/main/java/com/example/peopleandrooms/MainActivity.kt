package com.example.peopleandrooms

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.peopleandrooms.databinding.ActivityMainBinding
import com.example.peopleandrooms.ui.people.peopleFragment
import com.example.peopleandrooms.ui.rooms.RoomFragment
import dagger.hilt.android.AndroidEntryPoint

//peoplefragment may be wrong
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(peopleFragment())
        binding.bottomNav.setOnItemSelectedListener {
            when(it.itemId) {
                R.id.people_menu -> replaceFragment(peopleFragment())
                R.id.room_menu -> replaceFragment(RoomFragment()) }
            true
        } }
    private fun replaceFragment(fragment: Fragment){
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(binding.frameLayout.id, fragment).commit()
    }
}
