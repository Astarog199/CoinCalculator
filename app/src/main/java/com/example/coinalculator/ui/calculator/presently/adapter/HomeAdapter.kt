package com.example.coinalculator.ui.calculator.presently.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.coinalculator.databinding.ListItemBinding
import com.example.coinalculator.ui.calculator.domain.CoinCalculator

class HomeAdapter : RecyclerView.Adapter<HomeHolder>() {
    private var values: List<CoinCalculator> = emptyList()

    fun setData(data: List<CoinCalculator>) {
        this.values = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeHolder {
        return HomeHolder(
            ListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = values.size

    override fun onBindViewHolder(holder: HomeHolder, position: Int) {
        val item = values.getOrNull(position)
        with(holder.binding){
            listItem.text = item?.price.toString()
        }
    }
}

