package com.example.coinalculator.ui.home.presently.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.coinalculator.databinding.ListItemBinding
import kotlinx.coroutines.flow.StateFlow

class HomeAdapter : RecyclerView.Adapter<HomeHolder>() {
    private var values: List<String> = emptyList()

    fun setData(data: List<String>) {
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
            listItem.text = item
        }
    }
}

