package com.example.coinalculator.ui.card

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.coinalculator.databinding.ActivityCoinCardBinding

class CoinCardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCoinCardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCoinCardBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}