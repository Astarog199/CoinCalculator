package com.example.coinalculator.ui.calculator.presently.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.coinalculator.databinding.CalculatorItemBinding
import com.example.coinalculator.ui.calculator.presently.states.CoinCalState
import javax.inject.Inject

class CalculatorAdapter @Inject constructor(
    private val textChange:(Float, CoinCalState) -> Unit,
    private val showError: (Exception) -> Unit
) : RecyclerView.Adapter<CalculatorHolder>() {

    private var values: List<CoinCalState> = emptyList()

    fun setData(data: List<CoinCalState>) {
        this.values = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalculatorHolder {
        return CalculatorHolder(
            binding =  CalculatorItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            textChange = textChange,
            showError = showError
        )
    }

    override fun getItemCount(): Int = values.size

    override fun onBindViewHolder(holder: CalculatorHolder, position: Int) {
        val item = values.getOrNull(position)
        holder.bind(item)
    }
}

