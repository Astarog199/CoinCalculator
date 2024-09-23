package com.example.coinalculator.ui.coins.presently.list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.coinalculator.R
import com.example.coinalculator.ServiceLocator.applicationContext
import com.example.coinalculator.databinding.ItemBinding
import com.example.coinalculator.ui.coins.presently.list.states.CoinState


class CoinListAdapter(
    private val onClick: (CoinState) -> Unit
): RecyclerView.Adapter<CoinListHolder>() {
    private var values:List<CoinState> = emptyList()

    fun setData(data: List<CoinState>){
        this.values = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinListHolder {
        return CoinListHolder(
            ItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = values.size

    private fun formatChange24h(value: Float?, valuePercent: Float?, holder: CoinListHolder) : String {

        val green = ContextCompat.getColor(applicationContext, R.color.green)
        val red = ContextCompat.getColor(applicationContext, R.color.red)

        if (value != null && value < 0f){
            holder.binding.priceChange24h.setTextColor(red)
        }else{
            holder.binding.priceChange24h.setTextColor(green)
        }

        return String.format("%.2f",  value) +" $ · " + String.format("%.2f", valuePercent)+ " %"
    }

    override fun onBindViewHolder(holder: CoinListHolder, position: Int) {
        val item = values.getOrNull(position)
        with(holder.binding){
            image.load(item?.image)
            title.text = item?.name
            price.text = item?.price.toString()
                priceChange24h.text = formatChange24h(item?.priceChange24h, item?.pricePercentageChange24h, holder)

        }

        holder.binding.root.setOnClickListener{
            item?.let {
                onClick(item)
            }
        }
    }
}