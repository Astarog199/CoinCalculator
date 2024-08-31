package com.example.coinalculator.ui.calculator.presently.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.coinalculator.databinding.ListItemBinding
import com.example.coinalculator.ui.calculator.presently.states.CoinCalState

class HomeAdapter(
    private val textChange:(Float, CoinCalState) -> Unit,
    private val showError: (Exception) -> Unit
) : RecyclerView.Adapter<HomeHolder>() {

    private var values: List<CoinCalState> = emptyList()

    fun setData(data: List<CoinCalState>) {
        this.values = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeHolder {
        return HomeHolder(
            binding =  ListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            textChange = textChange,
            showError = showError
        )
    }

    override fun getItemCount(): Int = values.size

    override fun onBindViewHolder(holder: HomeHolder, position: Int) {
        val item = values.getOrNull(position)
        holder.bind(item)
    }
}

