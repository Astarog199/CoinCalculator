package com.example.coinalculator.ui.dashboard.presently.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.coinalculator.databinding.ItemBinding
import com.example.coinalculator.ui.dashboard.presently.CoinVO


class DashboardAdapter: RecyclerView.Adapter<DashboardHolder>() {
    private var values:List<CoinVO> = emptyList()

    fun setData(data: List<CoinVO>){
        this.values = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DashboardHolder {
        return DashboardHolder(
            ItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = values.size

    override fun onBindViewHolder(holder: DashboardHolder, position: Int) {
        val item = values.getOrNull(position)
        with(holder.binding){
            market.text = item?.market
            title.text = item?.name
            price.text = item?.price
        }
    }
}