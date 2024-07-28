package com.example.coinalculator.ui.coins.presently.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.coinalculator.databinding.ItemBinding
import com.example.coinalculator.ui.coins.presently.model.CoinState


class CoinsAdapter: RecyclerView.Adapter<CoinsHolder>() {
    private var values:List<CoinState> = emptyList()

    fun setData(data: List<CoinState>){
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