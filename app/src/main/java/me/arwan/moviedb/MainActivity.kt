package me.arwan.moviedb

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import me.arwan.moviedb.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.textviewHi.text = BuildConfig.RAPID_API_KEY
    }
}