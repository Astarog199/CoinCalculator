package com.example.coinalculator.ui.coins.presently.list.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.coinalculator.databinding.ItemBinding
import com.example.coinalculator.ui.coins.presently.list.states.CoinState
import javax.inject.Inject


class CoinListAdapter @Inject constructor(
    private val onClick: (CoinState) -> Unit
): RecyclerView.Adapter<CoinListHolder>() {
    private var values:List<CoinState> = emptyList()
    private lateinit var context: Context

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
            ),
            context = context,
            onClick = onClick
        )
    }

    override fun getItemCount(): Int = values.size

    override fun onBindViewHolder(holder: CoinListHolder, position: Int) {
        val item = values.getOrNull(position)
        holder.bind(item)
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        context = recyclerView.context
    }
}