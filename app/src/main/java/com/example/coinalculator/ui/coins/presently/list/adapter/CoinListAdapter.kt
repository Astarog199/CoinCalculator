package com.example.coinalculator.ui.coins.presently.list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
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

    override fun onBindViewHolder(holder: CoinListHolder, position: Int) {
        val item = values.getOrNull(position)
        with(holder.binding){
            title.text = item?.name
            price.text = item?.price
        }
        holder.binding.title.setOnClickListener{
            item?.let {
                onClick(item)
            }
        }
    }
}