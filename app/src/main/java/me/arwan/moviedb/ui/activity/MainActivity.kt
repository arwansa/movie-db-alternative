package me.arwan.moviedb.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import me.arwan.moviedb.R
import me.arwan.moviedb.databinding.ActivityMainBinding
import me.arwan.moviedb.ui.fragment.BlankFragment
import me.arwan.moviedb.ui.fragment.HomeFragment

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initBottomNavigation()
    }

    private fun initBottomNavigation() {
        binding.bottomNavigationBar.setOnItemSelectedListener {
            handleBottomNavigation(it.itemId)
            true
        }
        binding.bottomNavigationBar.selectedItemId = R.id.menu_home
    }

    private fun handleBottomNavigation(menuItemId: Int) = if (menuItemId == R.id.menu_home) {
        swapFragments(HomeFragment())
    } else {
        swapFragments(BlankFragment())
    }

    private fun swapFragments(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}