package com.example.coinalculator.ui.card

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.example.coinalculator.R
import com.example.coinalculator.databinding.ActivityCoinCardBinding
import com.example.coinalculator.ui.card.presentation.card.CoinCardFragment

class CoinCardActivity : AppCompatActivity() {
    private var id = ""
    private lateinit var binding: ActivityCoinCardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCoinCardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        id = intent.getParcelableExtra("item")!!

        supportFragmentManager.commit {
            replace<CoinCardFragment>(R.id.fragment_container)
            addToBackStack(CoinCardFragment::class.java.simpleName)
        }

    }
}