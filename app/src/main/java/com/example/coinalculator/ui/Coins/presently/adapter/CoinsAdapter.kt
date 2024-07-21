package com.example.coinalculator.ui.Coins.presently.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.coinalculator.databinding.ItemBinding
import com.example.coinalculator.ui.Coins.presently.CoinVO


class CoinsAdapter: RecyclerView.Adapter<CoinsHolder>() {
    private var values:List<CoinVO> = emptyList()

    fun setData(data: List<CoinVO>){
        this.values = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinsHolder {
        return CoinsHolder(
            ItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = values.size

    override fun onBindViewHolder(holder: CoinsHolder, position: Int) {
        val item = values.getOrNull(position)
        with(holder.binding){
            market.text = item?.market
            title.text = item?.name
            price.text = item?.price
        }
    }
}