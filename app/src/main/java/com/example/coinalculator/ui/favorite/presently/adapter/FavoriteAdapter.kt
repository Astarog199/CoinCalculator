package com.example.coinalculator.ui.favorite.presently.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.coinalculator.databinding.ItemBinding
import com.example.coinalculator.ui.favorite.presently.states.FavoriteStates

class FavoriteAdapter(
    private val onClick: (FavoriteStates) -> Unit
) : RecyclerView.Adapter <FavoriteHolder> () {
    private var values:List<FavoriteStates> = emptyList()

    fun setData(data: List<FavoriteStates>){
        this.values = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteHolder {
        return FavoriteHolder(
            ItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = values.size

    override fun onBindViewHolder(holder: FavoriteHolder, position: Int) {
        val item = values.getOrNull(position)
        with(holder.binding){
            market.text = item?.market
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