package com.example.coinalculator.ui.coins.presently.list.adapter

import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.coinalculator.R
import com.example.coinalculator.ServiceLocator.applicationContext
import com.example.coinalculator.databinding.ItemBinding
import com.example.coinalculator.ui.coins.presently.list.states.CoinState

class CoinListHolder(
    private val binding:ItemBinding,
    private val onClick: (CoinState) -> Unit
    ): RecyclerView.ViewHolder(binding.root) {
    fun bind(item: CoinState?) {
        with(binding){
            image.load(item?.image)
            title.text = item?.name
            price.text = getCost(item?.price)
            priceChange24h.text = formatChange24h(item?.priceChange24h, item?.pricePercentageChange24h)
        }
        binding.root.setOnClickListener{
            item?.let {
                onClick(item)
            }
        }
        if (item?.name == "rub") {
            binding.root.visibility = View.GONE
        }
    }

    private fun getCost(item: Float?): String {
        return when {
            item!! < 0.01 -> String.format("%.4f", item).replace(',', '.')
            item < 0.0001 -> "< 0.0001"
            item > 9999 ->  String.format("%.0f", item).replace(',', '.') +" $"
            else -> {
                String.format("%.2f", item).replace(',', '.') +" $"
            }
        }
    }

    private fun formatChange24h(value: Float?, valuePercent: Float?,) : String {

        val green = ContextCompat.getColor(applicationContext, R.color.green)
        val red = ContextCompat.getColor(applicationContext, R.color.red)

        if (value != null && value < 0f){
            binding.priceChange24h.setTextColor(red)
        }else{
            binding.priceChange24h.setTextColor(green)
        }

        return String.format("%.2f",  value) +" $ · " + String.format("%.2f", valuePercent)+ " %"
    }
}